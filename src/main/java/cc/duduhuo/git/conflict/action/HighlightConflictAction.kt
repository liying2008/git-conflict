package cc.duduhuo.git.conflict.action

import cc.duduhuo.git.conflict.Global
import cc.duduhuo.git.conflict.tool.DocumentTools
import cc.duduhuo.git.conflict.tool.NotificationTools.showNotification
import cc.duduhuo.git.conflict.tool.ext.addInDocumentListenerIfNot
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
        fun refreshHighlighters() {
            // all editors with conflicts
            val editors = Global.highlighterMap.filter { it.value.isNotEmpty() }.map { it.key }
            editors.forEach {
                // DocumentTools.showConflict() 会修改 Global.highlighterMap，所以不能直接在迭代 Global.highlighterMap 时调用，会导致 ConcurrentModificationException
                DocumentTools.showConflict(it)
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
        // Add document listener
        document.addInDocumentListenerIfNot(editor)
    }

    override fun update(e: AnActionEvent) {
        // Get required data keys
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isVisible = false
        val canShow = project != null && editor != null
        if (canShow) {
            // Set visibility only in case of existing project and editor
            if (Global.highlighterMap[editor].isNullOrEmpty()) {
                // No conflict highlighters
                e.presentation.isVisible = true
            }
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }
}
