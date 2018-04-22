package cc.duduhuo.git.conflict.setting;

import cc.duduhuo.git.conflict.*;
import cc.duduhuo.git.conflict.action.HighlightConflictAction;
import com.intellij.openapi.options.SearchableConfigurable;
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
public class ColorSettingsPanelImpl extends ColorSettingsPanel implements SearchableConfigurable {
    public ColorSettingsPanelImpl() {
        super();
        initUI();
    }

    private void initUI() {
        this.radioDefault.setSelected(false);
        this.radioDarcula.setSelected(false);
        if (GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.DEFAULT)) {
            System.out.println("default==");
            this.radioDefault.setSelected(true);
        } else if (GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.DARCULA)) {
            System.out.println("darcula==");
            this.radioDarcula.setSelected(true);
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
        return this.mainPanel;
    }

    @Override
    public boolean isModified() {
        if (this.radioDefault.isSelected()) {
            return !GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.DEFAULT);
        } else if (this.radioDarcula.isSelected()) {
            return !GlobalSettings.getMarkColor().equals(SettingsService.ColorSettings.DARCULA);
        }
        return false;
    }

    @Override
    public void reset() {
        radioDefault.setSelected(true);
        radioDarcula.setSelected(false);
//        GlobalSettings.getMarkColor().setMarkColor(SettingsService.ColorSettings.DEFAULT);
    }

    @Override
    public void apply() {
        if (radioDefault.isSelected()) {
            GlobalSettings.getMarkColor().setMarkColor(SettingsService.ColorSettings.DEFAULT);
            Global.sCurrentColor = SettingsService.ColorSettings.DEFAULT;
        } else if (radioDarcula.isSelected()) {
            GlobalSettings.getMarkColor().setMarkColor(SettingsService.ColorSettings.DARCULA);
            Global.sCurrentColor = SettingsService.ColorSettings.DARCULA;
        }
        TextAttr.loadTextAttr();
        HighlightConflictAction.refreshHighlight();
    }
}
