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
class AcceptCurrentChangeAction : AbsFixConflict() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val project = e.project
        fixConflict(editor, project, ACCEPT_CURRENT)
    }
}