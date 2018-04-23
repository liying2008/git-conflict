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
        if (GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.DEFAULT)) {
            cbColorScheme.setSelectedItem(DEFAULT);
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
        if (cbColorScheme.getSelectedItem() == DEFAULT) {
            return !GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.DEFAULT);
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
        if (cbColorScheme.getSelectedItem() == DEFAULT) {
            GlobalSettings.getMarkColor().setMarkColor(SettingsService.ColorSettings.DEFAULT);
            Global.sCurrentColor = SettingsService.ColorSettings.DEFAULT;
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
