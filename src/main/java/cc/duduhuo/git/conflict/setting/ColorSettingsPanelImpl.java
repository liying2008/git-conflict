package cc.duduhuo.git.conflict.setting;

import cc.duduhuo.git.conflict.BundleTools;
import cc.duduhuo.git.conflict.Constants;
import cc.duduhuo.git.conflict.GlobalSettings;
import cc.duduhuo.git.conflict.TextAttr;
import cc.duduhuo.git.conflict.action.HighlightConflictAction;
import cc.duduhuo.git.conflict.model.MarkColor;
import cc.duduhuo.git.conflict.model.PersistentState;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
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
        setListener();
    }

    private void setListener() {
        cbColorScheme.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedItem = (String) e.getItem();
                updateCustomPanelUI(mMarkColors.get(selectedItem));
            }
        });
        btnNew.addActionListener(e -> {
            String newSchemeName = JOptionPane.showInputDialog(mainPanel, "Please input new scheme name");
            if (mAllSchemeNames.contains(newSchemeName)) {
                JOptionPane.showMessageDialog(mainPanel, "Scheme name already exists!", "Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
            cCurrentColor.setText(Integer.toHexString(0));
            cCurrentTitleColor.setText(Integer.toHexString(0));
            cIncomingColor.setText(Integer.toHexString(0));
            cIncomingTitleColor.setText(Integer.toHexString(0));
            mAllSchemeNames.add(newSchemeName);
            cbColorScheme.addItem(newSchemeName);
            cbColorScheme.setSelectedItem(newSchemeName);
            panelCustom.setVisible(true);
            // add to markColor map
            mMarkColors.put(newSchemeName, new MarkColor(newSchemeName, false, 0, 0, 0, 0));
        });
        cCurrentColor.getDocument().addDocumentListener(new ColorFieldDocumentListener(ColorFieldDocumentListener.CURRENT));
        cCurrentTitleColor.getDocument().addDocumentListener(new ColorFieldDocumentListener(ColorFieldDocumentListener.CURRENT_TITLE));
        cIncomingColor.getDocument().addDocumentListener(new ColorFieldDocumentListener(ColorFieldDocumentListener.INCOMING));
        cIncomingTitleColor.getDocument().addDocumentListener(new ColorFieldDocumentListener(ColorFieldDocumentListener.INCOMING_TITLE));
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
        updateCustomPanelUI(currentColor);
    }

    private void updateCustomPanelUI(MarkColor color) {
        if (color == null) {
            return;
        }
        if (color.isBuiltIn()) {
            panelCustom.setVisible(false);
        } else {
            panelCustom.setVisible(true);
        }
        cCurrentColor.setText(Integer.toHexString(color.getCurrentColor()));
        cCurrentTitleColor.setText(Integer.toHexString(color.getCurrentTitleColor()));
        cIncomingColor.setText(Integer.toHexString(color.getIncomingColor()));
        cIncomingTitleColor.setText(Integer.toHexString(color.getIncomingTitleColor()));
    }

    private class ColorFieldDocumentListener implements DocumentListener {
        public static final int CURRENT = 0;
        public static final int CURRENT_TITLE = 1;
        public static final int INCOMING = 2;
        public static final int INCOMING_TITLE = 3;
        private int mFieldIndex;

        public ColorFieldDocumentListener(int fieldIndex) {
            mFieldIndex = fieldIndex;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {

        }

        @Override
        public void removeUpdate(DocumentEvent e) {

        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            String selectedItem = (String) cbColorScheme.getSelectedItem();
            if (mFieldIndex == CURRENT) {
                mMarkColors.get(selectedItem).setCurrentColor(Integer.valueOf(cCurrentColor.getText()));
                mMarkColors.get(selectedItem).setDarkCurrentColor(Integer.valueOf(cCurrentColor.getText()));
            } else if (mFieldIndex == CURRENT_TITLE) {
                mMarkColors.get(selectedItem).setCurrentTitleColor(Integer.valueOf(cCurrentTitleColor.getText()));
                mMarkColors.get(selectedItem).setDarkCurrentTitleColor(Integer.valueOf(cCurrentTitleColor.getText()));
            } else if (mFieldIndex == INCOMING) {
                mMarkColors.get(selectedItem).setIncomingColor(Integer.valueOf(cIncomingColor.getText()));
                mMarkColors.get(selectedItem).setDarkIncomingColor(Integer.valueOf(cIncomingColor.getText()));
            } else if (mFieldIndex == INCOMING_TITLE) {
                mMarkColors.get(selectedItem).setIncomingTitleColor(Integer.valueOf(cIncomingTitleColor.getText()));
                mMarkColors.get(selectedItem).setDarkIncomingTitleColor(Integer.valueOf(cIncomingTitleColor.getText()));
            }
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
