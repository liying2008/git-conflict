package cc.duduhuo.git.conflict.action

import cc.duduhuo.git.conflict.Global
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DefaultActionGroup

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 22:12
 * Description:
 * Remarks:
 * =======================================================
 */
class ConflictOpActionGroup : DefaultActionGroup() {

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
