package cc.duduhuo.git.conflict.action.operation

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 20:33
 * Description:
 * Remarks:
 * =======================================================
 */
class AcceptBothChangesAction : AbsFixConflict() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val project = e.project ?: return
        fixConflict(editor, project, ACCEPT_BOTH)
    }
}