package cc.duduhuo.git.conflict

import cc.duduhuo.git.conflict.model.ConflictItem
import cc.duduhuo.git.conflict.model.MarkColor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 20:59
 * Description:
 * Remarks:
 * =======================================================
 */
object Global {
    // is it highlighted
    val sIsHighlightMap: MutableMap<Editor, Boolean> = mutableMapOf()

    // conflict item list
    val sConflictItemMap: MutableMap<Document, MutableList<ConflictItem>> = mutableMapOf()

    // document listener map
    val sDocumentListenerMap: MutableMap<Document, InDocumentListener> = mutableMapOf()

    // current color scheme
    var sCurrentColor: MarkColor = MarkColor()
        get() {
            val state = GlobalSettings.getPersistentState()
            return state.markColors[state.schemeName]!!
        }
}
