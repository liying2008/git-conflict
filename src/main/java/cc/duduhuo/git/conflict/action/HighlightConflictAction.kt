package cc.duduhuo.git.conflict.action

import cc.duduhuo.git.conflict.Global
import cc.duduhuo.git.conflict.InDocumentListener
import cc.duduhuo.git.conflict.tool.DocumentTools
import cc.duduhuo.git.conflict.tool.NotificationTools.showNotification
import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager

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
            Global.isHighlightMap.forEach { (editor, isHighlight) ->
                if (isHighlight) {
                    DocumentTools.showConflict(editor)
                }
            }
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val document = editor.document
        val conflictsCount = DocumentTools.showConflict(editor)
        val fileEditorManager = FileEditorManager.getInstance(project)
        val selectedEditor = fileEditorManager.selectedEditor!!
        val file = selectedEditor.file
        if (conflictsCount == 0) {
            showNotification(
                file.name,
                "No conflicts detected.",
                NotificationType.INFORMATION,
                project,
                listOf(
                    object : NotificationAction("Open file") {
                        override fun actionPerformed(e: AnActionEvent, notification: Notification) {
                            fileEditorManager.openFile(file, true)
                            notification.expire()  // close notification
                        }
                    }
                )
            )
            return
        }

        showNotification(
            file.name,
            "The document has $conflictsCount conflict(s).",
            NotificationType.WARNING,
            project,
            listOf(
                object : NotificationAction("Open file") {
                    override fun actionPerformed(e: AnActionEvent, notification: Notification) {
                        fileEditorManager.openFile(file, true)
                        notification.expire()  // close notification
                    }
                }
            )
        )
        Global.isHighlightMap[editor] = true
        val oldListener = Global.documentListenerMap[document]
        if (oldListener == null) {
            val documentListener = InDocumentListener(editor)
            document.addDocumentListener(documentListener)
            Global.documentListenerMap[document] = documentListener
        }
    }

    override fun update(e: AnActionEvent) {
        // Get required data keys
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isVisible = false
        val canShow = project != null && editor != null
        if (canShow) {
            val isHighlight: Boolean = Global.isHighlightMap.getOrDefault(editor, false)
            // Set visibility only in case of existing project and editor
            if (!isHighlight) {
                e.presentation.isVisible = true
            }
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }
}
