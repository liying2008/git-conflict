package cc.duduhuo.git.conflict.action

import cc.duduhuo.git.conflict.Global.sDocumentListenerMap
import cc.duduhuo.git.conflict.Global.sIsHighlightMap
import cc.duduhuo.git.conflict.InDocumentListener
import cc.duduhuo.git.conflict.tool.DocumentTools.showConflict
import cc.duduhuo.git.conflict.tool.NotificationTools.showNotification
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Editor

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 9:53
 * Description:
 * Remarks:
 * =======================================================
 */
class HighlightConflictAction : AnAction() {

    companion object {
        fun refreshHighlight() {
            val keySet: Set<Editor> = sIsHighlightMap.keys
            for (editor in keySet) {
                if (sIsHighlightMap[editor]!!) {
                    showConflict(editor)
                }
            }
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val document = editor.document
        val hasConflict = showConflict(editor)
        if (!hasConflict) {
            showNotification(
                "No Git Conflict", "There is no conflict in the document.",
                NotificationType.INFORMATION
            )
            return
        }
        sIsHighlightMap[editor] = true
        val oldListener: InDocumentListener? = sDocumentListenerMap[document]
        if (oldListener == null) {
            val documentListener = InDocumentListener(editor)
            document.addDocumentListener(documentListener)
            sDocumentListenerMap[document] = documentListener
        }
    }

    override fun update(e: AnActionEvent) {
        // Get required data keys
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isVisible = false
        val canShow = project != null && editor != null
        if (canShow) {
            val isHighlight: Boolean = sIsHighlightMap.getOrDefault(editor, false)
            // Set visibility only in case of existing project and editor
            if (!isHighlight) {
                e.presentation.isVisible = true
            }
        }
    }
}
