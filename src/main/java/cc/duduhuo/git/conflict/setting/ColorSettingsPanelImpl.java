package cc.duduhuo.git.conflict.setting;

import cc.duduhuo.git.conflict.*;
import cc.duduhuo.git.conflict.action.HighlightConflictAction;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 16:50
 * Description:
 * Remarks:
 * =======================================================
 */
public class ColorSettingsPanelImpl extends ColorSettingsPanel {

    public ColorSettingsPanelImpl() {
        initUI();
    }

    private void initUI() {
        if (GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.AUTO)) {
            cbColorScheme.setSelectedItem(AUTO);
        } else if (GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.INTELLIJ)) {
            cbColorScheme.setSelectedItem(INTELLIJ);
        } else if (GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.DARCULA)) {
            cbColorScheme.setSelectedItem(DARCULA);
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
        if (cbColorScheme.getSelectedItem() == AUTO) {
            return !GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.AUTO);
        } else if (cbColorScheme.getSelectedItem() == INTELLIJ) {
            return !GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.INTELLIJ);
        } else if (cbColorScheme.getSelectedItem() == DARCULA) {
            return !GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.DARCULA);
        }
        return false;
    }

    @Override
    public void reset() {
    }

    @Override
    public void apply() {
        boolean isModified = isModified();
        if (cbColorScheme.getSelectedItem() == AUTO) {
            GlobalSettings.getMarkColor().setMarkColor(SettingsService.ColorSettings.AUTO);
            Global.sCurrentColor = SettingsService.ColorSettings.AUTO;
        } else if (cbColorScheme.getSelectedItem() == INTELLIJ) {
            GlobalSettings.getMarkColor().setMarkColor(SettingsService.ColorSettings.INTELLIJ);
            Global.sCurrentColor = SettingsService.ColorSettings.INTELLIJ;
        } else if (cbColorScheme.getSelectedItem() == DARCULA) {
            GlobalSettings.getMarkColor().setMarkColor(SettingsService.ColorSettings.DARCULA);
            Global.sCurrentColor = SettingsService.ColorSettings.DARCULA;
        }
        if (isModified) {
            TextAttr.loadTextAttr();
            HighlightConflictAction.refreshHighlight();
        }
    }
}
