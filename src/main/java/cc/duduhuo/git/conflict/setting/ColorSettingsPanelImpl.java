package cc.duduhuo.git.conflict.setting;

import cc.duduhuo.git.conflict.tool.BundleTools;
import cc.duduhuo.git.conflict.Constants;
import cc.duduhuo.git.conflict.GlobalSettings;
import cc.duduhuo.git.conflict.TextAttr;
import cc.duduhuo.git.conflict.action.HighlightConflictAction;
import cc.duduhuo.git.conflict.model.MarkColor;
import cc.duduhuo.git.conflict.model.PersistentState;
import cc.duduhuo.git.conflict.tool.Tools;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 16:50
 * Description:
 * Remarks:
 * =======================================================
 */
public class ColorSettingsPanelImpl extends ColorSettingsPanel {
    private Map<String, MarkColor> mMarkColors;
    private String mSchemeName;
    private List<String> mAllSchemeNames = new ArrayList<>();

    public ColorSettingsPanelImpl() {
        initUI();
        setListeners();
    }

    private void setListeners() {
        cCurrentColor.addMouseListener(new ColorChooserListener(ColorChooserListener.CURRENT));
        cCurrentTitleColor.addMouseListener(new ColorChooserListener(ColorChooserListener.CURRENT_TITLE));
        cIncomingColor.addMouseListener(new ColorChooserListener(ColorChooserListener.INCOMING));
        cIncomingTitleColor.addMouseListener(new ColorChooserListener(ColorChooserListener.INCOMING_TITLE));

        cbColorScheme.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedItem = (String) e.getItem();
                updateUI(mMarkColors.get(selectedItem));
            }
        });
        btnNew.addActionListener(e -> {
            String newSchemeName = JOptionPane.showInputDialog(mainPanel, "Please input new scheme name");
            if (newSchemeName == null) {
                return;
            }
            if ("".equals(newSchemeName)) {
                JOptionPane.showMessageDialog(mainPanel, "Scheme name can not be empty!", "Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (mAllSchemeNames.contains(newSchemeName)) {
                JOptionPane.showMessageDialog(mainPanel, "Scheme name already exists!", "Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            lbCurrentContent.setText("0x000000");
            lbCurrentTitle.setText("0x000000");
            lbIncomingContent.setText("0x000000");
            lbIncomingTitle.setText("0x000000");
            cCurrentColor.setBackground(JBColor.BLACK);
            cCurrentTitleColor.setBackground(JBColor.BLACK);
            cIncomingColor.setBackground(JBColor.BLACK);
            cIncomingTitleColor.setBackground(JBColor.BLACK);
            mAllSchemeNames.add(newSchemeName);
            cbColorScheme.addItem(newSchemeName);
            cbColorScheme.setSelectedItem(newSchemeName);
            MarkColor markColor = new MarkColor(newSchemeName, false, 0, 0, 0, 0);
            // add to markColor map
            mMarkColors.put(newSchemeName, markColor);
            updateUI(markColor);
        });
        btnDelete.addActionListener(e -> {
            String selectedItem = (String) cbColorScheme.getSelectedItem();
            mMarkColors.remove(selectedItem);
            cbColorScheme.removeItem(selectedItem);
            mAllSchemeNames.remove(selectedItem);
            updateUI(mMarkColors.get((String) cbColorScheme.getSelectedItem()));
        });
    }

    private void initUI() {
        PersistentState persistentState = GlobalSettings.getPersistentState();
        mMarkColors = persistentState.getMarkColors();
        mSchemeName = persistentState.getSchemeName();
        MarkColor currentColor;
        String name;
        Set<String> keySet = mMarkColors.keySet();
        for (String key : keySet) {
            name = mMarkColors.get(key).getSchemeName();
            cbColorScheme.addItem(name);
            mAllSchemeNames.add(name);
        }
        cbColorScheme.setSelectedItem(mSchemeName);
        currentColor = mMarkColors.get(mSchemeName);
        updateUI(currentColor);
    }

    private void updateUI(MarkColor color) {
        if (color == null) {
            panelCustom.setVisible(false);
            btnDelete.setEnabled(false);
            return;
        }
        if (color.isBuiltIn()) {
            panelCustom.setVisible(false);
            btnDelete.setEnabled(false);
        } else {
            panelCustom.setVisible(true);
            btnDelete.setEnabled(true);
        }
        lbCurrentContent.setText(Tools.int2HexString(color.getCurrentColor()));
        lbCurrentTitle.setText(Tools.int2HexString(color.getCurrentTitleColor()));
        lbIncomingContent.setText(Tools.int2HexString(color.getIncomingColor()));
        lbIncomingTitle.setText(Tools.int2HexString(color.getIncomingTitleColor()));
        cCurrentColor.setBackground(new JBColor(color.getCurrentColor(), color.getCurrentColor()));
        cCurrentTitleColor.setBackground(new JBColor(new Color(color.getCurrentTitleColor()), new Color(color.getCurrentTitleColor())));
        cIncomingColor.setBackground(new JBColor(new Color(color.getIncomingColor()), new Color(color.getIncomingColor())));
        cIncomingTitleColor.setBackground(new JBColor(new Color(color.getIncomingTitleColor()), new Color(color.getIncomingTitleColor())));
    }

    private class ColorChooserListener implements MouseListener {
        static final int CURRENT = 0;
        static final int CURRENT_TITLE = 1;
        static final int INCOMING = 2;
        static final int INCOMING_TITLE = 3;
        private int mFieldIndex;


        public ColorChooserListener(int index) {
            mFieldIndex = index;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Color chooseColor = JColorChooser.showDialog(mainPanel, "Choose a color", null);
            if (chooseColor != null) {
                String selectedItem = (String) cbColorScheme.getSelectedItem();
                String colorStr = Tools.color2HexString(chooseColor);
                if (mFieldIndex == CURRENT) {
                    cCurrentColor.setBackground(chooseColor);
                    lbCurrentContent.setText(colorStr);
                    mMarkColors.get(selectedItem).setCurrentColor(Integer.parseInt(colorStr.substring(2), 16));
                    mMarkColors.get(selectedItem).setDarkCurrentColor(Integer.parseInt(colorStr.substring(2), 16));
                } else if (mFieldIndex == CURRENT_TITLE) {
                    cCurrentTitleColor.setBackground(chooseColor);
                    lbCurrentTitle.setText(colorStr);
                    mMarkColors.get(selectedItem).setCurrentTitleColor(Integer.parseInt(colorStr.substring(2), 16));
                    mMarkColors.get(selectedItem).setDarkCurrentTitleColor(Integer.parseInt(colorStr.substring(2), 16));
                } else if (mFieldIndex == INCOMING) {
                    cIncomingColor.setBackground(chooseColor);
                    lbIncomingContent.setText(colorStr);
                    mMarkColors.get(selectedItem).setIncomingColor(Integer.parseInt(colorStr.substring(2), 16));
                    mMarkColors.get(selectedItem).setDarkIncomingColor(Integer.parseInt(colorStr.substring(2), 16));
                } else if (mFieldIndex == INCOMING_TITLE) {
                    cIncomingTitleColor.setBackground(chooseColor);
                    lbIncomingTitle.setText(colorStr);
                    mMarkColors.get(selectedItem).setIncomingTitleColor(Integer.parseInt(colorStr.substring(2), 16));
                    mMarkColors.get(selectedItem).setDarkIncomingTitleColor(Integer.parseInt(colorStr.substring(2), 16));
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    @NotNull
    @Override
    public String getId() {
        return BundleTools.getValue(Constants.BundleKey.SETTINGS_TITLE);
    }

    @Nls
    @Override
    public String getDisplayName() {
        return this.getId();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return mainPanel;
    }

    @Override
    public boolean isModified() {
        return cbColorScheme.getSelectedItem() != mSchemeName;
    }

    @Override
    public void reset() {
    }

    @Override
    public void apply() {
        boolean isModified = isModified();
        GlobalSettings.getPersistentState().setMarkColors(mMarkColors);
        GlobalSettings.getPersistentState().setSchemeName((String) cbColorScheme.getSelectedItem());
        //todo
        if (isModified) {
            TextAttr.loadTextAttr();
            HighlightConflictAction.refreshHighlight();
        }
    }
}
