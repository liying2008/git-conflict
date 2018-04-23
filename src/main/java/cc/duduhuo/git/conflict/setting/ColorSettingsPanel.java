package cc.duduhuo.git.conflict.setting;

import com.intellij.openapi.options.SearchableConfigurable;

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
    protected JRadioButton radioDefault;
    protected JRadioButton radioDarcula;
    protected JPanel mainPanel;
}
