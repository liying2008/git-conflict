package cc.duduhuo.git.conflict.action.operation

import cc.duduhuo.git.conflict.Global
import cc.duduhuo.git.conflict.model.ConflictItem
import cc.duduhuo.git.conflict.tool.DocumentTools
import cc.duduhuo.git.conflict.tool.NotificationTools.showNotification
import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/24 23:13
 * Description:
 * Remarks:
 * =======================================================
 */
abstract class AbsFixConflict : AnAction() {
    companion object {
        const val ACCEPT_CURRENT = 0
        const val ACCEPT_INCOMING = 1
        const val ACCEPT_BOTH = 2
    }

    /**
     * fix a conflict with the specified strategy.
     *
     * @param editor
     * @param project
     * @param strategy the strategy must be one of `ACCEPT_CURRENT`, `ACCEPT_INCOMING`, or `ACCEPT_BOTH` .
     */
    protected fun fixConflict(editor: Editor, project: Project, strategy: Int) {
        val document = editor.document
        if (!document.isWritable) {
            val fileEditorManager = FileEditorManager.getInstance(project)
            val selectedEditor = fileEditorManager.selectedEditor!!
            val file = selectedEditor.file
            showNotification(
                file.name,
                "The document is not writable.",
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
            return
        }
        val caretModel = editor.caretModel
        val offset = caretModel.offset
        val lineNumber = document.getLineNumber(offset)
        // Does the cursor fall in the area of conflict content
        var inConflictPosition = false
        val conflictItems = Global.conflictItemMap[document]!!
        for (item in conflictItems) {
            if (item.currentChangeMarkerLineNum <= lineNumber && item.incomingChangeMarkerLineNum >= lineNumber) {
                inConflictPosition = true
                val start = document.getLineStartOffset(item.currentChangeMarkerLineNum)
                val end = if (item.incomingChangeMarkerLineNum == document.lineCount - 1) {
                    document.getLineEndOffset(item.incomingChangeMarkerLineNum)
                } else {
                    document.getLineStartOffset(item.incomingChangeMarkerLineNum + 1)
                }
                val replaceStr = when (strategy) {
                    ACCEPT_CURRENT -> {
                        DocumentTools.getConflictSectionString(document, item, DocumentTools.SECTION_CURRENT)
                    }

                    ACCEPT_INCOMING -> {
                        DocumentTools.getConflictSectionString(document, item, DocumentTools.SECTION_INCOMING)
                    }

                    ACCEPT_BOTH -> {
                        val currentSection =
                            DocumentTools.getConflictSectionString(document, item, DocumentTools.SECTION_CURRENT)
                        val incomingSection =
                            DocumentTools.getConflictSectionString(document, item, DocumentTools.SECTION_INCOMING)
                        "${currentSection}${incomingSection}"
                    }

                    else -> {
                        // unreachable
                        ""
                    }
                }
                // Must use WriteCommandAction to change document text
                WriteCommandAction.runWriteCommandAction(project) {
                    document.replaceString(start, end, replaceStr)
                }
                break
            }
        }
        if (!inConflictPosition) {
            JBPopupFactory.getInstance().createMessage("Conflict content is not detected in this location.")
                .showInBestPositionFor(editor)
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }
}
