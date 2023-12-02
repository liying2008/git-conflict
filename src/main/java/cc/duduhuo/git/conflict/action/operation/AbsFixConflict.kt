package cc.duduhuo.git.conflict.action.operation

import cc.duduhuo.git.conflict.Global
import cc.duduhuo.git.conflict.model.ConflictItem
import cc.duduhuo.git.conflict.tool.NotificationTools.showNotification
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
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
const val ACCEPT_CURRENT = 0
const val ACCEPT_INCOMING = 1
const val ACCEPT_BOTH = 2

abstract class AbsFixConflict : AnAction() {

    @IntDef([ACCEPT_CURRENT, ACCEPT_INCOMING, ACCEPT_BOTH])
    @Retention(AnnotationRetention.SOURCE)
    annotation class STRATEGY

    protected fun fixConflict(editor: Editor, project: Project?, @STRATEGY strategy: Int) {
        val document = editor.document
        if (!document.isWritable) {
            showNotification(
                "Fix Git Conflict", "This document can not be written.",
                NotificationType.WARNING
            )
            return
        }
        val caretModel = editor.caretModel
        val offset = caretModel.offset
        val lineNumber = document.getLineNumber(offset)
        // Does the cursor fall in the area of conflict content
        var inConflictPosition = false
        val conflictItems: List<ConflictItem> = Global.conflictItemMap[document]!!
        for (item in conflictItems) {
            if (item.currentChangeMarkerLineNum <= lineNumber && item.incomingChangeMarkerLineNum >= lineNumber) {
                inConflictPosition = true
                val start = document.getLineStartOffset(item.currentChangeMarkerLineNum)
                val end = document.getLineEndOffset(item.incomingChangeMarkerLineNum)
                val replaceStr = when (strategy) {
                    ACCEPT_CURRENT -> {
                        item.currentChangeStr
                    }
                    ACCEPT_INCOMING -> {
                        item.incomingChangeStr
                    }
                    ACCEPT_BOTH -> {
                        "${item.currentChangeStr}\n${item.incomingChangeStr}"
                    }
                    else -> {
                        ""
                    }
                }
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
}
