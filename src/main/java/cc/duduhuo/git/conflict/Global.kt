package cc.duduhuo.git.conflict

import cc.duduhuo.git.conflict.model.ConflictItem
import cc.duduhuo.git.conflict.model.MarkColor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.markup.RangeHighlighter

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 20:59
 * Description:
 * Remarks:
 * =======================================================
 */
object Global {
    // highlighter map to editor
    val highlighterMap: MutableMap<Editor, List<RangeHighlighter>> = mutableMapOf()

    // conflict item map to document
    val conflictItemMap: MutableMap<Document, List<ConflictItem>> = mutableMapOf()

    // document listener map to document
    val documentListenerMap: MutableMap<Document, InDocumentListener> = mutableMapOf()

    // current color scheme
    // must be var
    var currentColor: MarkColor = BuiltInColor.DEFAULT
        get() {
            val state = GlobalSettings.getPersistentState()
            return state.markColors[state.schemeName]!!.toMarkColor()
        }
}
