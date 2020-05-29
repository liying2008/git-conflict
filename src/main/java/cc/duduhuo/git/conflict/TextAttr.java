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
public final class TextAttr {
    public static TextAttributes sCurrentTitleAttributes;
    public static TextAttributes sCurrentAttributes;
    public static TextAttributes sIncomingTitleAttributes;
    public static TextAttributes sIncomingAttributes;

    static {
        loadTextAttr();
    }

    public static void loadTextAttr() {
        sCurrentTitleAttributes = new TextAttributes(null,
            new JBColor(Global.sCurrentColor.getCurrentTitleColor(), Global.sCurrentColor.getDarkCurrentTitleColor()),
            null, EffectType.ROUNDED_BOX, Font.PLAIN);
        sCurrentAttributes = new TextAttributes(null,
            new JBColor(Global.sCurrentColor.getCurrentColor(), Global.sCurrentColor.getDarkCurrentColor()),
            null, EffectType.ROUNDED_BOX, Font.PLAIN);
        sIncomingTitleAttributes = new TextAttributes(null,
            new JBColor(Global.sCurrentColor.getIncomingTitleColor(), Global.sCurrentColor.getDarkIncomingTitleColor()),
            null, EffectType.ROUNDED_BOX, Font.PLAIN);
        sIncomingAttributes = new TextAttributes(null,
            new JBColor(Global.sCurrentColor.getIncomingColor(), Global.sCurrentColor.getDarkIncomingColor()),
            null, EffectType.ROUNDED_BOX, Font.PLAIN);
    }
}
