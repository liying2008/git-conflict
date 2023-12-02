package cc.duduhuo.git.conflict.setting

import cc.duduhuo.git.conflict.BuiltInColor
import cc.duduhuo.git.conflict.Constants
import cc.duduhuo.git.conflict.Global
import cc.duduhuo.git.conflict.GlobalSettings
import cc.duduhuo.git.conflict.TextAttr
import cc.duduhuo.git.conflict.action.HighlightConflictAction
import cc.duduhuo.git.conflict.model.MarkColor
import cc.duduhuo.git.conflict.model.PersistentMarkColor
import cc.duduhuo.git.conflict.tool.ext.formatHexA
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ItemEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JColorChooser
import javax.swing.JComponent
import javax.swing.JOptionPane
import kotlin.collections.LinkedHashMap

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 16:50
 * Description: Settings panel's implementation
 * Remarks:
 * =======================================================
 */
const val CURRENT_CONTENT = 0
const val CURRENT_HEADER = 1
const val INCOMING_CONTENT = 2
const val INCOMING_HEADER = 3
const val COMMON_CONTENT = 4
const val COMMON_HEADER = 5

class ColorSettingsPanelImpl : ColorSettingsPanel() {
    private val markColorInUse = MarkColor()
    private lateinit var markColorMap: LinkedHashMap<String, MarkColor>
    private lateinit var schemeInUse: String
    private val allSchemeNames: MutableList<String> = mutableListOf()

    init {
        initUI()
        setListeners()
    }

    private fun convertPersistentMarkColorMapToMarkColorMap(persistentMarkColorMap: LinkedHashMap<String, PersistentMarkColor>): LinkedHashMap<String, MarkColor> {
        val markColorMap = LinkedHashMap<String, MarkColor>()
        persistentMarkColorMap.forEach { (schemeName, persistentMarkColor) ->
            markColorMap[schemeName] = persistentMarkColor.toMarkColor()
        }
        return markColorMap
    }

    private fun convertMarkColorMapToPersistentMarkColorMap(markColorMap: LinkedHashMap<String, MarkColor>): LinkedHashMap<String, PersistentMarkColor> {
        val persistentMarkColorMap = LinkedHashMap<String, PersistentMarkColor>()
        markColorMap.forEach { (schemeName, markColor) ->
            persistentMarkColorMap[schemeName] = PersistentMarkColor.of(markColor)
        }
        return persistentMarkColorMap
    }

    private fun initUI() {
        val persistentState = GlobalSettings.getPersistentState()
        markColorMap = convertPersistentMarkColorMapToMarkColorMap(persistentState.markColors)
        schemeInUse = persistentState.schemeName

        // make sure the built-in color is displayed at the top.
        cbColorScheme.addItem(BuiltInColor.DEFAULT_SCHEME_NAME)

        markColorMap.forEach { (schemeName, markColor) ->
            if (schemeName != BuiltInColor.DEFAULT_SCHEME_NAME) {
                cbColorScheme.addItem(schemeName)
            }
            allSchemeNames.add(schemeName)
        }
        // Double check.
        if (schemeInUse in markColorMap) {
            cbColorScheme.selectedItem = schemeInUse
            markColorInUse.copyFrom(markColorMap[schemeInUse]!!)
        } else {
            cbColorScheme.selectedItem = BuiltInColor.DEFAULT_SCHEME_NAME
            markColorInUse.copyFrom(BuiltInColor.DEFAULT)
        }
        updateUI(markColorInUse)
    }

    private fun updateUI(color: MarkColor?) {
        if (color == null) {
            panelCustom.isVisible = false
            btnDelete.isEnabled = false
            return
        }
        if (color.isBuiltIn) {
            panelCustom.isVisible = false
            btnDelete.isEnabled = false
        } else {
            panelCustom.isVisible = true
            btnDelete.isEnabled = true
        }
        lbCurrentContent.text = color.currentContentBackground.formatHexA()
        lbCurrentHeader.text = color.currentHeaderBackground.formatHexA()
        lbIncomingContent.text = color.incomingContentBackground.formatHexA()
        lbIncomingHeader.text = color.incomingHeaderBackground.formatHexA()
        lbCommonContent.text = color.commonContentBackground.formatHexA()
        lbCommonHeader.text = color.commonHeaderBackground.formatHexA()
        lbCurrentContentBackground.background = color.currentContentBackground
        lbCurrentHeaderBackground.background = color.currentHeaderBackground
        lbIncomingContentBackground.background = color.incomingContentBackground
        lbIncomingHeaderBackground.background = color.incomingHeaderBackground
        lbCommonContentBackground.background = color.commonContentBackground
        lbCommonHeaderBackground.background = color.commonHeaderBackground
    }

    private fun setListeners() {
        lbCurrentContentBackground.addMouseListener(ColorChooserListener(CURRENT_CONTENT))
        lbCurrentHeaderBackground.addMouseListener(ColorChooserListener(CURRENT_HEADER))
        lbIncomingContentBackground.addMouseListener(ColorChooserListener(INCOMING_CONTENT))
        lbIncomingHeaderBackground.addMouseListener(ColorChooserListener(INCOMING_HEADER))
        lbCommonContentBackground.addMouseListener(ColorChooserListener(COMMON_CONTENT))
        lbCommonHeaderBackground.addMouseListener(ColorChooserListener(COMMON_HEADER))

        // Call when the selected item is changed.
        cbColorScheme.addItemListener { e: ItemEvent ->
            if (e.stateChange == ItemEvent.SELECTED) {
                val selectedItem = e.item as String
                updateUI(markColorMap[selectedItem])
            }
        }
        // New color scheme
        btnNew.addActionListener { e: ActionEvent? ->
            val newSchemeName = JOptionPane.showInputDialog(mainPanel, "Please input new scheme name: ")
                ?: return@addActionListener
            if ("" == newSchemeName) {
                JOptionPane.showMessageDialog(
                    mainPanel, "Scheme name can not be empty!", "Failed",
                    JOptionPane.ERROR_MESSAGE
                )
                return@addActionListener
            }
            if (allSchemeNames.contains(newSchemeName)) {
                JOptionPane.showMessageDialog(
                    mainPanel, "Scheme name already exists!", "Failed",
                    JOptionPane.ERROR_MESSAGE
                )
                return@addActionListener
            }
            lbCurrentContent.text = BuiltInColor.DEFAULT.currentContentBackground.formatHexA()
            lbCurrentHeader.text = BuiltInColor.DEFAULT.currentHeaderBackground.formatHexA()
            lbIncomingContent.text = BuiltInColor.DEFAULT.incomingContentBackground.formatHexA()
            lbIncomingHeader.text = BuiltInColor.DEFAULT.incomingHeaderBackground.formatHexA()
            lbCommonContent.text = BuiltInColor.DEFAULT.commonContentBackground.formatHexA()
            lbCommonHeader.text = BuiltInColor.DEFAULT.commonHeaderBackground.formatHexA()
            lbCurrentContentBackground.background = BuiltInColor.DEFAULT.currentContentBackground
            lbCurrentHeaderBackground.background = BuiltInColor.DEFAULT.currentHeaderBackground
            lbIncomingContentBackground.background = BuiltInColor.DEFAULT.incomingContentBackground
            lbIncomingHeaderBackground.background = BuiltInColor.DEFAULT.incomingHeaderBackground
            lbCommonContentBackground.background = BuiltInColor.DEFAULT.commonContentBackground
            lbCommonHeaderBackground.background = BuiltInColor.DEFAULT.commonHeaderBackground
            allSchemeNames.add(newSchemeName)
            cbColorScheme.addItem(newSchemeName)
            cbColorScheme.selectedItem = newSchemeName
            // add to markColor map
            markColorMap[newSchemeName] = MarkColor(newSchemeName, false)
            updateUI(markColorMap[newSchemeName])
        }
        // delete a color scheme
        btnDelete.addActionListener { e: ActionEvent? ->
            val selectedItem = cbColorScheme.selectedItem as String
            val confirm = JOptionPane.showConfirmDialog(
                mainPanel,
                "Delete scheme: $selectedItem ?",
                "Delete",
                JOptionPane.OK_CANCEL_OPTION
            )
            if (confirm == JOptionPane.OK_OPTION) {
                val markColor = markColorMap[selectedItem]!!
                // Double check. Prevent the built-in color scheme from being deleted.
                if (!markColor.isBuiltIn) {
                    markColorMap.remove(selectedItem)
                    cbColorScheme.removeItem(selectedItem)
                    allSchemeNames.remove(selectedItem)
                } else {
                    JOptionPane.showMessageDialog(
                        mainPanel, "The built-in color scheme cannot be deleted.",
                        "Failed", JOptionPane.ERROR_MESSAGE
                    )
                }
            }
        }
    }

    private inner class ColorChooserListener(private val fieldIndex: Int) : MouseListener {

        override fun mouseClicked(e: MouseEvent) {
            val selectedItem = cbColorScheme.selectedItem as String
            val tempColor = markColorMap[selectedItem]!!
            val initialColor: Color? = when (fieldIndex) {
                CURRENT_CONTENT -> {
                    tempColor.currentContentBackground
                }

                CURRENT_HEADER -> {
                    tempColor.currentHeaderBackground
                }

                INCOMING_CONTENT -> {
                    tempColor.incomingContentBackground
                }

                INCOMING_HEADER -> {
                    tempColor.incomingHeaderBackground
                }

                COMMON_CONTENT -> {
                    tempColor.commonContentBackground
                }

                COMMON_HEADER -> {
                    tempColor.commonHeaderBackground
                }

                else -> null
            }
            val chooseColor = JColorChooser.showDialog(mainPanel, "Choose a color", initialColor)
            if (chooseColor != null) {
                when (fieldIndex) {
                    CURRENT_CONTENT -> {
                        lbCurrentContentBackground.background = chooseColor
                        lbCurrentContent.text = chooseColor.formatHexA()
                        tempColor.currentContentBackground = chooseColor
                    }

                    CURRENT_HEADER -> {
                        lbCurrentHeaderBackground.background = chooseColor
                        lbCurrentHeader.text = chooseColor.formatHexA()
                        tempColor.currentHeaderBackground = chooseColor
                    }

                    INCOMING_CONTENT -> {
                        lbIncomingContentBackground.background = chooseColor
                        lbIncomingContent.text = chooseColor.formatHexA()
                        tempColor.incomingContentBackground = chooseColor
                    }

                    INCOMING_HEADER -> {
                        lbIncomingHeaderBackground.background = chooseColor
                        lbIncomingHeader.text = chooseColor.formatHexA()
                        tempColor.incomingHeaderBackground = chooseColor
                    }

                    COMMON_CONTENT -> {
                        lbCommonContentBackground.background = chooseColor
                        lbCommonContent.text = chooseColor.formatHexA()
                        tempColor.commonContentBackground = chooseColor
                    }

                    COMMON_HEADER -> {
                        lbCommonHeaderBackground.background = chooseColor
                        lbCommonHeader.text = chooseColor.formatHexA()
                        tempColor.commonHeaderBackground = chooseColor
                    }
                }
                markColorMap[selectedItem] = tempColor
            }
        }

        override fun mousePressed(e: MouseEvent) {}
        override fun mouseReleased(e: MouseEvent) {}
        override fun mouseEntered(e: MouseEvent) {}
        override fun mouseExited(e: MouseEvent) {}
    }

    override fun getId(): String {
        return Constants.Resource.SETTINGS_TITLE
    }

    override fun getDisplayName(): String {
        return this.id
    }

    override fun createComponent(): JComponent? {
        return mainPanel
    }

    override fun isModified(): Boolean {
        return if (cbColorScheme.selectedItem !== schemeInUse) {
            true
        } else {
            markColorInUse != markColorMap[cbColorScheme.selectedItem as String]
        }
    }

    override fun reset() {
        cbColorScheme.selectedItem = schemeInUse
    }

    override fun apply() {
        val isModified = isModified
        val persistentState = GlobalSettings.getPersistentState()
        persistentState.markColors = convertMarkColorMapToPersistentMarkColorMap(markColorMap)
        persistentState.schemeName = (cbColorScheme.selectedItem as String)
        if (isModified) {
            schemeInUse = cbColorScheme.selectedItem as String
            markColorInUse.copyFrom(markColorMap[schemeInUse]!!)
            Global.currentColor = markColorInUse
            TextAttr.loadTextAttr()
            HighlightConflictAction.refreshHighlight()
        }
    }
}
