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
    lateinit var currentHeaderAttributes: TextAttributes
    lateinit var currentContentAttributes: TextAttributes
    lateinit var commonHeaderAttributes: TextAttributes
    lateinit var commonContentAttributes: TextAttributes
    lateinit var incomingHeaderAttributes: TextAttributes
    lateinit var incomingContentAttributes: TextAttributes

    init {
        loadTextAttr()
    }

    fun loadTextAttr() {
        currentHeaderAttributes = TextAttributes(
            null,
            Global.currentColor.currentHeaderBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
        currentContentAttributes = TextAttributes(
            null,
            Global.currentColor.currentContentBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
        commonHeaderAttributes = TextAttributes(
            null,
            Global.currentColor.commonHeaderBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
        commonContentAttributes = TextAttributes(
            null,
            Global.currentColor.commonContentBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
        incomingHeaderAttributes = TextAttributes(
            null,
            Global.currentColor.incomingHeaderBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
        incomingContentAttributes = TextAttributes(
            null,
            Global.currentColor.incomingContentBackground,
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
    }
}
