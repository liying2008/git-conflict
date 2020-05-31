package cc.duduhuo.git.conflict.model

import cc.duduhuo.git.conflict.BuiltInColor

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 17:21
 * Description:
 * Remarks:
 * =======================================================
 */
data class MarkColor(
    var schemeName: String = BuiltInColor.AUTO.schemeName,
    var isBuiltIn: Boolean = BuiltInColor.AUTO.isBuiltIn,
    var currentTitleColor: Int = BuiltInColor.AUTO.currentTitleColor,
    var currentColor: Int = BuiltInColor.AUTO.currentColor,
    var incomingTitleColor: Int = BuiltInColor.AUTO.incomingTitleColor,
    var incomingColor: Int = BuiltInColor.AUTO.incomingColor,
    var darkCurrentTitleColor: Int = BuiltInColor.AUTO.darkCurrentTitleColor,
    var darkCurrentColor: Int = BuiltInColor.AUTO.darkCurrentColor,
    var darkIncomingTitleColor: Int = BuiltInColor.AUTO.darkIncomingTitleColor,
    var darkIncomingColor: Int = BuiltInColor.AUTO.darkIncomingColor
) {
    constructor(schemeName: String, isBuiltIn: Boolean, currentTitleColor: Int, currentColor: Int, incomingTitleColor: Int, incomingColor: Int)
        : this(schemeName, isBuiltIn, currentTitleColor, currentColor, incomingTitleColor, incomingColor, currentTitleColor, currentColor, incomingTitleColor, incomingColor)

    fun setMarkColor(markColor: MarkColor) {
        schemeName = markColor.schemeName
        isBuiltIn = markColor.isBuiltIn
        currentColor = markColor.currentColor
        currentTitleColor = markColor.currentTitleColor
        incomingColor = markColor.incomingColor
        incomingTitleColor = markColor.incomingTitleColor
        darkCurrentColor = markColor.darkCurrentColor
        darkCurrentTitleColor = markColor.darkCurrentTitleColor
        darkIncomingColor = markColor.darkIncomingColor
        darkIncomingTitleColor = markColor.darkIncomingTitleColor
    }

    fun copy(markColor: MarkColor): MarkColor {
        schemeName = markColor.schemeName
        isBuiltIn = markColor.isBuiltIn
        currentColor = markColor.currentColor
        currentTitleColor = markColor.currentTitleColor
        incomingColor = markColor.incomingColor
        incomingTitleColor = markColor.incomingTitleColor
        darkCurrentColor = markColor.darkCurrentColor
        darkCurrentTitleColor = markColor.darkCurrentTitleColor
        darkIncomingColor = markColor.darkIncomingColor
        darkIncomingTitleColor = markColor.darkIncomingTitleColor
        return this
    }
}
