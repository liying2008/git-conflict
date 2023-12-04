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
    val isHighlightMap: MutableMap<Editor, Boolean> = mutableMapOf()

    // conflict item list
    val conflictItemMap: MutableMap<Document, MutableList<ConflictItem>> = mutableMapOf()

    // document listener map
    val documentListenerMap: MutableMap<Document, InDocumentListener> = mutableMapOf()

    // current color scheme
    // must be var
    var currentColor: MarkColor = BuiltInColor.DEFAULT
        get() {
            val state = GlobalSettings.getPersistentState()
            return state.markColors[state.schemeName]!!.toMarkColor()
        }
}
