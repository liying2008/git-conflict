package cc.duduhuo.git.conflict;

import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;

import java.awt.*;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 20:23
 * Description:
 * Remarks:
 * =======================================================
 */
public class TextAttr {
    public static TextAttributes sCurrentTitleAttributes;
    public static TextAttributes sCurrentAttributes;
    public static TextAttributes sIncomingTitleAttributes;
    public static TextAttributes sIncomingAttributes;

    static {
        sCurrentTitleAttributes = new TextAttributes(null, new JBColor(new Color(47, 115, 102), new Color(47, 115, 102)),
            null, EffectType.ROUNDED_BOX, 0);
        sCurrentAttributes = new TextAttributes(null, new JBColor(new Color(37, 64, 59), new Color(37, 64, 59)),
            null, EffectType.ROUNDED_BOX, 0);
        sIncomingTitleAttributes = new TextAttributes(null, new JBColor(new Color(47, 98, 143), new Color(47, 98, 143)),
            null, EffectType.ROUNDED_BOX, 0);
        sIncomingAttributes = new TextAttributes(null, new JBColor(new Color(37, 57, 75), new Color(37, 57, 75)),
            null, EffectType.ROUNDED_BOX, 0);

    }
}
