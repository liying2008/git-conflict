package cc.duduhuo.git.conflict.model

import cc.duduhuo.git.conflict.BuiltInColor
import java.awt.Color

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 17:21
 * Description:
 * Remarks:
 * =======================================================
 */
data class MarkColor(
    var schemeName: String = BuiltInColor.DEFAULT.schemeName,
    var isBuiltIn: Boolean = BuiltInColor.DEFAULT.isBuiltIn,
    var currentHeaderBackground: Color = BuiltInColor.DEFAULT.currentHeaderBackground,
    var currentContentBackground: Color = BuiltInColor.DEFAULT.currentContentBackground,
    var incomingHeaderBackground: Color = BuiltInColor.DEFAULT.incomingHeaderBackground,
    var incomingContentBackground: Color = BuiltInColor.DEFAULT.incomingContentBackground,
    var commonHeaderBackground: Color = BuiltInColor.DEFAULT.commonHeaderBackground,
    var commonContentBackground: Color = BuiltInColor.DEFAULT.commonContentBackground,
) {

    fun copyFrom(markColor: MarkColor): MarkColor {
        schemeName = markColor.schemeName
        isBuiltIn = markColor.isBuiltIn
        currentHeaderBackground = markColor.currentHeaderBackground
        currentContentBackground = markColor.currentContentBackground
        incomingHeaderBackground = markColor.incomingHeaderBackground
        incomingContentBackground = markColor.incomingContentBackground
        commonHeaderBackground = markColor.commonHeaderBackground
        commonContentBackground = markColor.commonContentBackground
        return this
    }
}
