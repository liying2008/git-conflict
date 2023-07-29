package cc.duduhuo.git.conflict.setting;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;

import javax.swing.JButton;
import javax.swing.JPanel;

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
    protected JBLabel cCurrentTitleColor;
    protected JBLabel cCurrentColor;
    protected JBLabel cIncomingTitleColor;
    protected JBLabel cIncomingColor;
    protected JBLabel lbCurrentTitle;
    protected JBLabel lbIncomingTitle;
    protected JBLabel lbCurrentContent;
    protected JBLabel lbIncomingContent;
    protected JButton btnDelete;
}
