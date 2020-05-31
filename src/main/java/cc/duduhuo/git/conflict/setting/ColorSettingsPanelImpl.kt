package cc.duduhuo.git.conflict.setting

import cc.duduhuo.git.conflict.*
import cc.duduhuo.git.conflict.action.HighlightConflictAction.Companion.refreshHighlight
import cc.duduhuo.git.conflict.model.MarkColor
import cc.duduhuo.git.conflict.tool.BundleTools.getValue
import cc.duduhuo.git.conflict.tool.Tools.color2HexString
import cc.duduhuo.git.conflict.tool.Tools.int2HexString
import com.intellij.ui.JBColor
import org.jetbrains.annotations.Nls
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
const val CURRENT = 0
const val CURRENT_TITLE = 1
const val INCOMING = 2
const val INCOMING_TITLE = 3

class ColorSettingsPanelImpl : ColorSettingsPanel() {
    private val mOldMarkColor = MarkColor()
    private lateinit var mMarkColors: LinkedHashMap<String, MarkColor>
    private lateinit var mSchemeName: String
    private val mAllSchemeNames: MutableList<String> = mutableListOf()

    init {
        initUI()
        setListeners()
    }

    private fun initUI() {
        val persistentState = GlobalSettings.getPersistentState()
        val map = persistentState.markColors
        mSchemeName = persistentState.schemeName
        // clone markColors map
        @SuppressWarnings("unchecked")
        mMarkColors = map.clone() as LinkedHashMap<String, MarkColor>

        // make sure the built-in color is displayed at the top.
        cbColorScheme.addItem(BuiltInColor.AUTO_SCHEME_NAME)
        cbColorScheme.addItem(BuiltInColor.INTELLIJ_SCHEME_NAME)
        cbColorScheme.addItem(BuiltInColor.DARCULA_SCHEME_NAME)
        val keySet: Set<String> = map.keys
        for (key in keySet) {
            if (key != BuiltInColor.AUTO_SCHEME_NAME &&
                key != BuiltInColor.INTELLIJ_SCHEME_NAME &&
                key != BuiltInColor.DARCULA_SCHEME_NAME
            ) {
                cbColorScheme.addItem(key)
            }
            mAllSchemeNames.add(key)
        }
        // Double check.
        if (keySet.contains(mSchemeName)) {
            cbColorScheme.selectedItem = mSchemeName
            mOldMarkColor.copy(map[mSchemeName]!!)
        } else {
            cbColorScheme.selectedItem = BuiltInColor.DEFAULT_SCHEME_NAME
            mOldMarkColor.copy(BuiltInColor.DEFAULT_MARK_COLOR)
        }
        updateUI(mOldMarkColor)
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
        lbCurrentContent.text = int2HexString(color.currentColor)
        lbCurrentTitle.text = int2HexString(color.currentTitleColor)
        lbIncomingContent.text = int2HexString(color.incomingColor)
        lbIncomingTitle.text = int2HexString(color.incomingTitleColor)
        cCurrentColor.background = JBColor(color.currentColor, color.currentColor)
        cCurrentTitleColor.background = JBColor(color.currentTitleColor, color.currentTitleColor)
        cIncomingColor.background = JBColor(color.incomingColor, color.incomingColor)
        cIncomingTitleColor.background = JBColor(color.incomingTitleColor, color.incomingTitleColor)
    }

    private fun setListeners() {
        cCurrentColor.addMouseListener(ColorChooserListener(CURRENT))
        cCurrentTitleColor.addMouseListener(ColorChooserListener(CURRENT_TITLE))
        cIncomingColor.addMouseListener(ColorChooserListener(INCOMING))
        cIncomingTitleColor.addMouseListener(ColorChooserListener(INCOMING_TITLE))

        // Call when the selected item is changed.
        cbColorScheme.addItemListener { e: ItemEvent ->
            if (e.stateChange == ItemEvent.SELECTED) {
                val selectedItem = e.item as String
                updateUI(mMarkColors[selectedItem])
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
            if (mAllSchemeNames.contains(newSchemeName)) {
                JOptionPane.showMessageDialog(
                    mainPanel, "Scheme name already exists!", "Failed",
                    JOptionPane.ERROR_MESSAGE
                )
                return@addActionListener
            }
            lbCurrentContent.text = "0x000000"
            lbCurrentTitle.text = "0x000000"
            lbIncomingContent.text = "0xffffff"
            lbIncomingTitle.text = "0xffffff"
            cCurrentColor.background = JBColor.BLACK
            cCurrentTitleColor.background = JBColor.BLACK
            cIncomingColor.background = JBColor.WHITE
            cIncomingTitleColor.background = JBColor.WHITE
            mAllSchemeNames.add(newSchemeName)
            cbColorScheme.addItem(newSchemeName)
            cbColorScheme.selectedItem = newSchemeName
            val markColor = MarkColor(
                newSchemeName, false, 0x000000,
                0x000000, 0xffffff, 0xffffff
            )
            // add to markColor map
            mMarkColors[newSchemeName] = markColor
            updateUI(markColor)
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
                val markColor = mMarkColors[selectedItem]
                // Double check. Prevent the built-in color scheme from being deleted.
                if (!markColor!!.isBuiltIn) {
                    mMarkColors.remove(selectedItem)
                    cbColorScheme.removeItem(selectedItem)
                    mAllSchemeNames.remove(selectedItem)
                } else {
                    JOptionPane.showMessageDialog(
                        mainPanel, "The built-in color scheme cannot be deleted.",
                        "Failed", JOptionPane.ERROR_MESSAGE
                    )
                }
            }
        }
    }

    private inner class ColorChooserListener(private val mFieldIndex: Int) : MouseListener {

        override fun mouseClicked(e: MouseEvent) {
            val selectedItem = cbColorScheme.selectedItem as String
            val tempColor = mMarkColors[selectedItem]!!
            val initialColor: Color? = when (mFieldIndex) {
                CURRENT -> {
                    JBColor(tempColor.currentColor, tempColor.currentColor)
                }
                CURRENT_TITLE -> {
                    JBColor(tempColor.currentTitleColor, tempColor.currentTitleColor)
                }
                INCOMING -> {
                    JBColor(tempColor.incomingColor, tempColor.incomingColor)
                }
                INCOMING_TITLE -> {
                    JBColor(tempColor.incomingTitleColor, tempColor.incomingTitleColor)
                }
                else -> null
            }
            val chooseColor = JColorChooser.showDialog(mainPanel, "Choose a color", initialColor)
            if (chooseColor != null) {
                val rgb = chooseColor.rgb
                val colorStr = color2HexString(chooseColor)
                when (mFieldIndex) {
                    CURRENT -> {
                        cCurrentColor.background = JBColor(rgb, rgb)
                        lbCurrentContent.text = colorStr
                        tempColor.currentColor = colorStr.substring(2).toInt(16)
                        tempColor.darkCurrentColor = colorStr.substring(2).toInt(16)
                    }
                    CURRENT_TITLE -> {
                        cCurrentTitleColor.background = JBColor(rgb, rgb)
                        lbCurrentTitle.text = colorStr
                        tempColor.currentTitleColor = colorStr.substring(2).toInt(16)
                        tempColor.darkCurrentTitleColor = colorStr.substring(2).toInt(16)
                    }
                    INCOMING -> {
                        cIncomingColor.background = JBColor(rgb, rgb)
                        lbIncomingContent.text = colorStr
                        tempColor.incomingColor = colorStr.substring(2).toInt(16)
                        tempColor.darkIncomingColor = colorStr.substring(2).toInt(16)
                    }
                    INCOMING_TITLE -> {
                        cIncomingTitleColor.background = JBColor(rgb, rgb)
                        lbIncomingTitle.text = colorStr
                        tempColor.incomingTitleColor = colorStr.substring(2).toInt(16)
                        tempColor.darkIncomingTitleColor = colorStr.substring(2).toInt(16)
                    }
                }
                mMarkColors[selectedItem] = tempColor
            }
        }

        override fun mousePressed(e: MouseEvent) {}
        override fun mouseReleased(e: MouseEvent) {}
        override fun mouseEntered(e: MouseEvent) {}
        override fun mouseExited(e: MouseEvent) {}
    }

    override fun getId(): String {
        return getValue(Constants.BundleKey.SETTINGS_TITLE)
    }

    override fun getDisplayName(): @Nls String? {
        return this.id
    }

    override fun createComponent(): JComponent? {
        return mainPanel
    }

    override fun isModified(): Boolean {
        return if (cbColorScheme.selectedItem !== mSchemeName) {
            true
        } else {
            mOldMarkColor != mMarkColors[cbColorScheme.selectedItem as String]
        }
    }

    override fun reset() {}
    override fun apply() {
        val isModified = isModified
        val persistentState = GlobalSettings.getPersistentState()
        persistentState.markColors = mMarkColors.clone() as LinkedHashMap<String, MarkColor>
        persistentState.schemeName = (cbColorScheme.selectedItem as String)
        if (isModified) {
            mSchemeName = cbColorScheme.selectedItem as String
            mOldMarkColor.copy(mMarkColors[mSchemeName]!!)
            Global.sCurrentColor = mOldMarkColor
            TextAttr.loadTextAttr()
            refreshHighlight()
        }
    }
}