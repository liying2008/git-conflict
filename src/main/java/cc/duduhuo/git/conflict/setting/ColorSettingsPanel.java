package cc.duduhuo.git.conflict.setting;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.ComboBox;

import javax.swing.*;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 16:44
 * Description:
 * Remarks:
 * =======================================================
 */
public abstract class ColorSettingsPanel implements SearchableConfigurable {
    protected static final String DEFAULT = "Default";
    protected static final String DARCULA = "Darcula";
    protected JPanel mainPanel;
    protected ComboBox<String> cbColorScheme;

    private void createUIComponents() {
        String[] schemes = new String[]{DEFAULT, DARCULA};
        cbColorScheme = new ComboBox<>(schemes);
    }
}
