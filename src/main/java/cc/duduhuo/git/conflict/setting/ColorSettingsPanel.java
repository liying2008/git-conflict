package cc.duduhuo.git.conflict.setting;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 16:44
 * Description:
 * Remarks:
 * =======================================================
 */
public abstract class ColorSettingsPanel implements SearchableConfigurable {
    protected static final String AUTO = "Auto";
    protected static final String INTELLIJ = "IntelliJ";
    protected static final String DARCULA = "Darcula";
    protected JPanel mainPanel;
    protected ComboBox<String> cbColorScheme;
    protected JButton btnNew;
    protected JPanel panelCustom;
    protected JBTextField cCurrentTitleColor;
    protected JBTextField cCurrentColor;
    protected JBTextField cIncomingTitleColor;
    protected JBTextField cIncomingColor;
}
