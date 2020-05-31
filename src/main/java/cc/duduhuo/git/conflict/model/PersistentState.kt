package cc.duduhuo.git.conflict.model

import cc.duduhuo.git.conflict.BuiltInColor

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/25 21:22
 * Description:
 * Remarks:
 * =======================================================
 */
data class PersistentState(
    var schemeName: String = BuiltInColor.DEFAULT_SCHEME_NAME,
    var markColors: LinkedHashMap<String, MarkColor> = LinkedHashMap(
        mutableMapOf(
            BuiltInColor.AUTO_SCHEME_NAME to BuiltInColor.AUTO,
            BuiltInColor.INTELLIJ_SCHEME_NAME to BuiltInColor.INTELLIJ,
            BuiltInColor.DARCULA_SCHEME_NAME to BuiltInColor.DARCULA
        )
    )
)
