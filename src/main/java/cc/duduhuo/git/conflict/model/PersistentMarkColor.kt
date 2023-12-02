package cc.duduhuo.git.conflict.model

import cc.duduhuo.git.conflict.tool.ext.formatHexA
import cc.duduhuo.git.conflict.tool.ext.fromHex

data class PersistentMarkColor(
    var schemeName: String = "",
    var isBuiltIn: Boolean = false,
    var currentHeaderBackground: String? = null,
    var currentContentBackground: String? = null,
    var incomingHeaderBackground: String? = null,
    var incomingContentBackground: String? = null,
    var commonHeaderBackground: String? = null,
    var commonContentBackground: String? = null,
) {
    companion object {
        fun of(markColor: MarkColor): PersistentMarkColor {
            return PersistentMarkColor(
                schemeName = markColor.schemeName,
                isBuiltIn = markColor.isBuiltIn,
                currentHeaderBackground = markColor.currentHeaderBackground.formatHexA(),
                currentContentBackground = markColor.currentContentBackground.formatHexA(),
                incomingHeaderBackground = markColor.incomingHeaderBackground.formatHexA(),
                incomingContentBackground = markColor.incomingContentBackground.formatHexA(),
                commonHeaderBackground = markColor.commonHeaderBackground.formatHexA(),
                commonContentBackground = markColor.commonContentBackground.formatHexA(),
            )
        }
    }

    fun toMarkColor(): MarkColor {
        return MarkColor(
            schemeName = schemeName,
            isBuiltIn = isBuiltIn,
            currentHeaderBackground = fromHex(currentHeaderBackground),
            currentContentBackground = fromHex(currentContentBackground),
            incomingHeaderBackground = fromHex(incomingHeaderBackground),
            incomingContentBackground = fromHex(incomingContentBackground),
            commonHeaderBackground = fromHex(commonHeaderBackground),
            commonContentBackground = fromHex(commonContentBackground),
        )
    }
}
