package cc.duduhuo.git.conflict;

import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;

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
            new JBColor(Global.sCurrentColor.getCurrentTitleColor(), Global.sCurrentColor.getCurrentTitleColor()),
            null, EffectType.ROUNDED_BOX, 0);
        sCurrentAttributes = new TextAttributes(null,
            new JBColor(Global.sCurrentColor.getCurrentColor(), Global.sCurrentColor.getCurrentColor()),
            null, EffectType.ROUNDED_BOX, 0);
        sIncomingTitleAttributes = new TextAttributes(null,
            new JBColor(Global.sCurrentColor.getIncomingTitleColor(), Global.sCurrentColor.getIncomingTitleColor()),
            null, EffectType.ROUNDED_BOX, 0);
        sIncomingAttributes = new TextAttributes(null,
            new JBColor(Global.sCurrentColor.getIncomingColor(), Global.sCurrentColor.getIncomingColor()),
            null, EffectType.ROUNDED_BOX, 0);
    }
}
