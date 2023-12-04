package cc.duduhuo.git.conflict.setting;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;

import javax.swing.JButton;
import javax.swing.JLabel;
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
    protected JButton btnDelete;
    protected JPanel panelCustom;
    protected JBLabel lbCurrentHeaderBackground;
    protected JBLabel lbCurrentHeader;
    protected JBLabel lbCurrentContentBackground;
    protected JBLabel lbCurrentContent;
    protected JBLabel lbIncomingHeaderBackground;
    protected JBLabel lbIncomingHeader;
    protected JBLabel lbIncomingContentBackground;
    protected JBLabel lbIncomingContent;
    protected JLabel lbCommonHeaderBackground;
    protected JLabel lbCommonHeader;
    protected JLabel lbCommonContentBackground;
    protected JLabel lbCommonContent;
}
