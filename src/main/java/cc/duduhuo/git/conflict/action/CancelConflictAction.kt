package cc.duduhuo.git.conflict.action

import cc.duduhuo.git.conflict.Global
import cc.duduhuo.git.conflict.tool.ext.removeConflictHighlightersIfAny
import cc.duduhuo.git.conflict.tool.ext.removeInDocumentListenerIfExist
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 20:57
 * Description:
 * Remarks:
 * =======================================================
 */
class CancelConflictAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val document = editor.document

        editor.removeConflictHighlightersIfAny()
        Global.conflictItemMap.remove(document)
        // remove document listener
        document.removeInDocumentListenerIfExist()
    }

    override fun update(e: AnActionEvent) {
        // Get required data keys
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isVisible = false
        val canShow = project != null && editor != null
        if (canShow) {
            // Set visibility only in case of existing project and editor
            if (!Global.highlighterMap[editor].isNullOrEmpty()) {
                // have conflict highlights
                e.presentation.isVisible = true
            }
        }
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }
}
