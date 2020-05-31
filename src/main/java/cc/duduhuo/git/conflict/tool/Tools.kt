package cc.duduhuo.git.conflict.tool

import java.awt.Color

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/26 21:06
 * Description:
 * Remarks:
 * =======================================================
 */
object Tools {
    fun color2HexString(color: Color): String {
        val sb = StringBuilder()
        var r = Integer.toHexString(color.red)
        var g = Integer.toHexString(color.green)
        var b = Integer.toHexString(color.blue)
        r = if (r.length == 1) "0$r" else r
        g = if (g.length == 1) "0$g" else g
        b = if (b.length == 1) "0$b" else b
        sb.append("0x")
        sb.append(r)
        sb.append(g)
        sb.append(b)
        // return like 0x000000
        return sb.toString()
    }

    fun int2HexString(color: Int): String {
        val sb = StringBuilder(Integer.toHexString(color))
        val count = 6 - sb.length
        for (i in 0 until count) {
            sb.insert(0, "0")
        }
        sb.insert(0, "0x")
        return sb.toString()
    }
}
