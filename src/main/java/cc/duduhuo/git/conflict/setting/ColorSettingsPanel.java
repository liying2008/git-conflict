package cc.duduhuo.git.conflict.setting;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBTextField;

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
    protected JPanel mainPanel;
    protected ComboBox<String> cbColorScheme;
    protected JButton btnNew;
    protected JPanel panelCustom;
    protected JLabel cCurrentTitleColor;
    protected JLabel cCurrentColor;
    protected JLabel cIncomingTitleColor;
    protected JLabel cIncomingColor;
    protected JLabel lbCurrentTitle;
    protected JLabel lbIncomingTitle;
    protected JLabel lbCurrentContent;
    protected JLabel lbIncomingContent;
    protected JButton btnDelete;
}
