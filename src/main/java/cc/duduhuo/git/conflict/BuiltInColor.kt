package cc.duduhuo.git.conflict

import cc.duduhuo.git.conflict.model.MarkColor
import cc.duduhuo.git.conflict.tool.ext.transparent
import java.awt.Color

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/25 21:28
 * Description:
 * Remarks:
 * =======================================================
 */
@Suppress("UseJBColor")
object BuiltInColor {
    const val DEFAULT_SCHEME_NAME = "VS Code"

    private const val HEADER_TRANSPARENCY = 0.5f
    private const val CONTENT_TRANSPARENCY = 0.4f
    private const val RULER_TRANSPARENCY = 1f

    private val currentBaseColor = Color(0x40C8AE).transparent(HEADER_TRANSPARENCY)
    private val incomingBaseColor = Color(0x40A6FF).transparent(HEADER_TRANSPARENCY)
    private val commonBaseColor = Color(0x606060).transparent(0.4f)

    val DEFAULT = MarkColor(
        DEFAULT_SCHEME_NAME, true,
        currentBaseColor,
        currentBaseColor.transparent(CONTENT_TRANSPARENCY),
        incomingBaseColor,
        incomingBaseColor.transparent(CONTENT_TRANSPARENCY),
        commonBaseColor,
        commonBaseColor.transparent(CONTENT_TRANSPARENCY)
    )
}
