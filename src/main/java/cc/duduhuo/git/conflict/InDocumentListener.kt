package cc.duduhuo.git.conflict

import cc.duduhuo.git.conflict.tool.DocumentTools
import cc.duduhuo.git.conflict.tool.ext.removeInDocumentListenerIfExist
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

    override fun documentChanged(event: DocumentEvent) {
        if (editor.isDisposed) {
            println("editor is disposed: $editor")
            return
        }
        val conflictsCount = DocumentTools.showConflict(editor)
        if (conflictsCount == 0) {
            val document = editor.document
            // remove document listener
            document.removeInDocumentListenerIfExist()
        }
    }
}
