package cc.duduhuo.git.conflict

import cc.duduhuo.git.conflict.Global.sConflictItemMap
import cc.duduhuo.git.conflict.Global.sIsHighlightMap
import cc.duduhuo.git.conflict.tool.DocumentTools.showConflict
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 10:37
 * Description:
 * Remarks:
 * =======================================================
 */
class InDocumentListener(private val mEditor: Editor) : DocumentListener {

    override fun documentChanged(event: DocumentEvent) {
        val hasConflict = showConflict(mEditor)
        if (!hasConflict) {
            val document = mEditor.document
            sIsHighlightMap[mEditor] = false
            sConflictItemMap.remove(document)
            val listener = Global.sDocumentListenerMap[document]
            if (listener != null) {
                document.removeDocumentListener(listener)
                Global.sDocumentListenerMap.remove(document)
            }
        }
    }
}