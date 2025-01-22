package cc.duduhuo.git.conflict

import cc.duduhuo.git.conflict.tool.DocumentTools
import cc.duduhuo.git.conflict.tool.ext.removeInDocumentListenerIfExist
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.project.Project

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 10:37
 * Description:
 * Remarks:
 * =======================================================
 */
class InDocumentListener(private val document: Document, private val project: Project) : DocumentListener {

    override fun documentChanged(event: DocumentEvent) {
        // println("documentChanged: ${document}, ${event}")
        // TODO highlight conflicts only when the document has highlighters
        val conflictsCount = DocumentTools.showConflict(document, project)
        if (conflictsCount == 0) {
            // remove document listener
            document.removeInDocumentListenerIfExist()
        }
    }
}
