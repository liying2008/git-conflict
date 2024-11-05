package cc.duduhuo.git.conflict

import com.intellij.openapi.editor.markup.EffectType
import com.intellij.openapi.editor.markup.TextAttributes
import java.awt.Font

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 20:23
 * Description:
 * Remarks:
 * =======================================================
 */
object TextAttr {
    fun getCurrentHeaderAttributes(): TextAttributes {
        return TextAttributes(
            null,
            Global.currentColor.currentHeaderBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
    }

    fun getCurrentContentAttributes(): TextAttributes {
        return TextAttributes(
            null,
            Global.currentColor.currentContentBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
    }

    fun getCommonHeaderAttributes(): TextAttributes {
        return TextAttributes(
            null,
            Global.currentColor.commonHeaderBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
    }

    fun getCommonContentAttributes(): TextAttributes {
        return TextAttributes(
            null,
            Global.currentColor.commonContentBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
    }

    fun getIncomingHeaderAttributes(): TextAttributes {
        return TextAttributes(
            null,
            Global.currentColor.incomingHeaderBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
    }

    fun getIncomingContentAttributes(): TextAttributes {
        return TextAttributes(
            null,
            Global.currentColor.incomingContentBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
    }
}
