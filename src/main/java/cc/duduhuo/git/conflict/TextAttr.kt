package cc.duduhuo.git.conflict

import com.intellij.openapi.editor.markup.EffectType
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.ui.JBColor
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
    lateinit var sCurrentTitleAttributes: TextAttributes
    lateinit var sCurrentAttributes: TextAttributes
    lateinit var sIncomingTitleAttributes: TextAttributes
    lateinit var sIncomingAttributes: TextAttributes

    init {
        loadTextAttr()
    }

    fun loadTextAttr() {
        sCurrentTitleAttributes = TextAttributes(
            null,
            JBColor(
                Global.sCurrentColor!!.currentTitleColor,
                Global.sCurrentColor!!.darkCurrentTitleColor
            ),
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
        sCurrentAttributes = TextAttributes(
            null,
            JBColor(
                Global.sCurrentColor!!.currentColor,
                Global.sCurrentColor!!.darkCurrentColor
            ),
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
        sIncomingTitleAttributes = TextAttributes(
            null,
            JBColor(
                Global.sCurrentColor!!.incomingTitleColor,
                Global.sCurrentColor!!.darkIncomingTitleColor
            ),
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
        sIncomingAttributes = TextAttributes(
            null,
            JBColor(
                Global.sCurrentColor!!.incomingColor,
                Global.sCurrentColor!!.darkIncomingColor
            ),
            null, EffectType.ROUNDED_BOX, Font.PLAIN
        )
    }
}
