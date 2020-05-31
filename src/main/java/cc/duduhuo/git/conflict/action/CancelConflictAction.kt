package cc.duduhuo.git.conflict.action

import cc.duduhuo.git.conflict.Global
import cc.duduhuo.git.conflict.Global.sConflictItemMap
import cc.duduhuo.git.conflict.Global.sIsHighlightMap
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
        sIsHighlightMap[editor] = false
        sConflictItemMap.remove(document)
        val markupModel = editor.markupModel
        markupModel.removeAllHighlighters()
        // remove document listener
        val listener = Global.sDocumentListenerMap[document]
        if (listener != null) {
            document.removeDocumentListener(listener)
            Global.sDocumentListenerMap.remove(document)
        }
    }

    override fun update(e: AnActionEvent) {
        // Get required data keys
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)
        // Set visibility only in case of existing project and editor
        e.presentation.isVisible = false
        val canShow = project != null && editor != null
        if (canShow) {
            val isHighlight: Boolean = sIsHighlightMap.getOrDefault(editor, false)
            if (isHighlight) {
                e.presentation.isVisible = true
            }
        }
    }
}