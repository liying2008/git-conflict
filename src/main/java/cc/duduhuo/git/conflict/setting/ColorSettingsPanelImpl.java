package cc.duduhuo.git.conflict.setting;

import cc.duduhuo.git.conflict.*;
import cc.duduhuo.git.conflict.tool.BundleTools;
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
import java.util.*;
import java.util.List;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 16:50
 * Description:
 * Remarks:
 * =======================================================
 */
public class ColorSettingsPanelImpl extends ColorSettingsPanel {
    private MarkColor mOldMarkColor = new MarkColor();
    private LinkedHashMap<String, MarkColor> mMarkColors;
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
            String newSchemeName = JOptionPane.showInputDialog(mainPanel, "Please input new scheme name: ");
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
            lbIncomingContent.setText("0xffffff");
            lbIncomingTitle.setText("0xffffff");
            cCurrentColor.setBackground(JBColor.BLACK);
            cCurrentTitleColor.setBackground(JBColor.BLACK);
            cIncomingColor.setBackground(JBColor.WHITE);
            cIncomingTitleColor.setBackground(JBColor.WHITE);
            mAllSchemeNames.add(newSchemeName);
            cbColorScheme.addItem(newSchemeName);
            cbColorScheme.setSelectedItem(newSchemeName);
            MarkColor markColor = new MarkColor(newSchemeName, false, 0x000000,
                0x000000, 0xffffff, 0xffffff);
            // add to markColor map
            mMarkColors.put(newSchemeName, markColor);
            updateUI(markColor);
        });
        btnDelete.addActionListener(e -> {
            String selectedItem = (String) cbColorScheme.getSelectedItem();
            int confirm = JOptionPane.showConfirmDialog(mainPanel, "Delete scheme: " + selectedItem + " ?", "Delete", JOptionPane.OK_CANCEL_OPTION);
            if (confirm == JOptionPane.OK_OPTION) {
                mMarkColors.remove(selectedItem);
                cbColorScheme.removeItem(selectedItem);
                mAllSchemeNames.remove(selectedItem);
                updateUI(mMarkColors.get((String) cbColorScheme.getSelectedItem()));
            }
        });
    }

    private void initUI() {
        PersistentState persistentState = GlobalSettings.getPersistentState();
        mMarkColors = persistentState.getMarkColors();
        mSchemeName = persistentState.getSchemeName();
        String name;
        // make sure the built-in color is displayed at the top.
        cbColorScheme.addItem(BuiltInColor.AUTO_SCHEME_NAME);
        cbColorScheme.addItem(BuiltInColor.INTELLIJ_SCHEME_NAME);
        cbColorScheme.addItem(BuiltInColor.DARCULA_SCHEME_NAME);
        Set<String> keySet = mMarkColors.keySet();
        for (String key : keySet) {
            name = mMarkColors.get(key).getSchemeName();
            if (!name.equals(BuiltInColor.AUTO_SCHEME_NAME) &&
                !name.equals(BuiltInColor.INTELLIJ_SCHEME_NAME) &&
                !name.equals(BuiltInColor.DARCULA_SCHEME_NAME)) {
                cbColorScheme.addItem(name);
            }
            mAllSchemeNames.add(name);
        }
        cbColorScheme.setSelectedItem(mSchemeName);
        mOldMarkColor.copy(mMarkColors.get(mSchemeName));
        updateUI(mOldMarkColor);
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
        cCurrentTitleColor.setBackground(new JBColor(color.getCurrentTitleColor(), color.getCurrentTitleColor()));
        cIncomingColor.setBackground(new JBColor(color.getIncomingColor(), color.getIncomingColor()));
        cIncomingTitleColor.setBackground(new JBColor(color.getIncomingTitleColor(), color.getIncomingTitleColor()));
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
            String selectedItem = (String) cbColorScheme.getSelectedItem();
            MarkColor tempColor = mMarkColors.get(selectedItem);
            Color initialColor = null;
            if (mFieldIndex == CURRENT) {
                initialColor = new JBColor(tempColor.getCurrentColor(), tempColor.getCurrentColor());
            } else if (mFieldIndex == CURRENT_TITLE) {
                initialColor = new JBColor(tempColor.getCurrentTitleColor(), tempColor.getCurrentTitleColor());
            } else if (mFieldIndex == INCOMING) {
                initialColor = new JBColor(tempColor.getIncomingColor(), tempColor.getIncomingColor());
            } else if (mFieldIndex == INCOMING_TITLE) {
                initialColor = new JBColor(tempColor.getIncomingTitleColor(), tempColor.getIncomingTitleColor());
            }

            Color chooseColor = JColorChooser.showDialog(mainPanel, "Choose a color", initialColor);
            if (chooseColor != null) {
                int rgb = chooseColor.getRGB();
                String colorStr = Tools.color2HexString(chooseColor);
                if (mFieldIndex == CURRENT) {
                    cCurrentColor.setBackground(new JBColor(rgb, rgb));
                    lbCurrentContent.setText(colorStr);
                    tempColor.setCurrentColor(Integer.parseInt(colorStr.substring(2), 16));
                    tempColor.setDarkCurrentColor(Integer.parseInt(colorStr.substring(2), 16));
                } else if (mFieldIndex == CURRENT_TITLE) {
                    cCurrentTitleColor.setBackground(new JBColor(rgb, rgb));
                    lbCurrentTitle.setText(colorStr);
                    tempColor.setCurrentTitleColor(Integer.parseInt(colorStr.substring(2), 16));
                    tempColor.setDarkCurrentTitleColor(Integer.parseInt(colorStr.substring(2), 16));
                } else if (mFieldIndex == INCOMING) {
                    cIncomingColor.setBackground(new JBColor(rgb, rgb));
                    lbIncomingContent.setText(colorStr);
                    tempColor.setIncomingColor(Integer.parseInt(colorStr.substring(2), 16));
                    tempColor.setDarkIncomingColor(Integer.parseInt(colorStr.substring(2), 16));
                } else if (mFieldIndex == INCOMING_TITLE) {
                    cIncomingTitleColor.setBackground(new JBColor(rgb, rgb));
                    lbIncomingTitle.setText(colorStr);
                    tempColor.setIncomingTitleColor(Integer.parseInt(colorStr.substring(2), 16));
                    tempColor.setDarkIncomingTitleColor(Integer.parseInt(colorStr.substring(2), 16));
                }
                mMarkColors.put(selectedItem, tempColor);
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
        if (cbColorScheme.getSelectedItem() != mSchemeName) {
            return true;
        } else {
            return !mOldMarkColor.equals(mMarkColors.get((String) cbColorScheme.getSelectedItem()));
        }
    }

    @Override
    public void reset() {
    }

    @Override
    public void apply() {
        boolean isModified = isModified();
        GlobalSettings.getPersistentState().setMarkColors(mMarkColors);
        GlobalSettings.getPersistentState().setSchemeName((String) cbColorScheme.getSelectedItem());
        if (isModified) {
            mSchemeName = (String) cbColorScheme.getSelectedItem();
            mOldMarkColor.copy(mMarkColors.get(mSchemeName));
            Global.sCurrentColor = mOldMarkColor;
            TextAttr.loadTextAttr();
            HighlightConflictAction.refreshHighlight();
        }
    }
}
