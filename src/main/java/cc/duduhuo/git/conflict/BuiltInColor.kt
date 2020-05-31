package cc.duduhuo.git.conflict

import cc.duduhuo.git.conflict.model.MarkColor

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/25 21:28
 * Description:
 * Remarks:
 * =======================================================
 */
object BuiltInColor {
    const val AUTO_SCHEME_NAME = "Auto"
    const val INTELLIJ_SCHEME_NAME = "IntelliJ"
    const val DARCULA_SCHEME_NAME = "Darcula"

    val AUTO = MarkColor(AUTO_SCHEME_NAME, true, 0x9fe3d6, 0xd9f4ef, 0x9fd2ff, 0xd9edff,
        0x2f7366, 0x25403b, 0x2f628f, 0x25394b)

    val INTELLIJ = MarkColor(INTELLIJ_SCHEME_NAME, true, 0x9fe3d6, 0xd9f4ef, 0x9fd2ff, 0xd9edff)

    val DARCULA = MarkColor(DARCULA_SCHEME_NAME, true, 0x2f7366, 0x25403b, 0x2f628f, 0x25394b)

    const val DEFAULT_SCHEME_NAME = AUTO_SCHEME_NAME

    val DEFAULT_MARK_COLOR = AUTO
}
