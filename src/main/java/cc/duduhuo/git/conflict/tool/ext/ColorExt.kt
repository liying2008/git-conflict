@file:Suppress("UseJBColor")

package cc.duduhuo.git.conflict.tool.ext

import java.awt.Color
import kotlin.math.roundToInt

/**
 * 设置透明度
 */
fun Color.transparent(factor: Float): Color {
    return Color(this.red, this.green, this.blue, (this.alpha * factor).roundToInt())
}

/**
 * Formats the color as #RRGGBBAA
 */
fun Color.formatHexA(): String {
    val sb = StringBuilder()
    var r = Integer.toHexString(this.red)
    var g = Integer.toHexString(this.green)
    var b = Integer.toHexString(this.blue)
    var a = Integer.toHexString(this.alpha)
    r = if (r.length == 1) "0$r" else r
    g = if (g.length == 1) "0$g" else g
    b = if (b.length == 1) "0$b" else b
    a = if (a.length == 1) "0$a" else a
    sb.append("#")
    sb.append(r)
    sb.append(g)
    sb.append(b)
    sb.append(a)
    // return like #00000000
    return sb.toString()
}

/**
 * Convert hex string to color
 *
 * @param hex #RRGGBBAA or #RRGGBB
 * @param default default color when hex is null or invalid
 */
fun fromHex(hex: String?, default: Color = Color.WHITE): Color {
    if (hex.isNullOrEmpty()) return default
    val trimmed = hex.trimStart('#')
    return if (trimmed.length == 6 || trimmed.length == 8) {
        val r = Integer.parseInt(trimmed.substring(0, 2), 16)
        val g = Integer.parseInt(trimmed.substring(2, 4), 16)
        val b = Integer.parseInt(trimmed.substring(4, 6), 16)
        val a = if (trimmed.length == 8) Integer.parseInt(trimmed.substring(6, 8), 16) else 255
        Color(r, g, b, a)
    } else {
        default
    }
}
