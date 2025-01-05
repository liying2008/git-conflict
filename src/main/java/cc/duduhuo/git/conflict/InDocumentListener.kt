package cc.duduhuo.git.conflict

import cc.duduhuo.git.conflict.tool.DocumentTools
import com.intellij.openapi.diagnostic.Logger
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
class InDocumentListener(private val editor: Editor) : DocumentListener {
    private val logger = Logger.getInstance(InDocumentListener::class.java)

    override fun documentChanged(event: DocumentEvent) {
        if (editor.isDisposed) {
            logger.info("editor is disposed: $editor")
            return
        }
        val conflictsCount = DocumentTools.showConflict(editor)
        if (conflictsCount == 0) {
            val document = editor.document
            Global.isHighlightMap[editor] = false
            Global.conflictItemMap.remove(document)
            val listener = Global.documentListenerMap[document]
            if (listener != null) {
                document.removeDocumentListener(listener)
                Global.documentListenerMap.remove(document)
            }
        }
    }
}
