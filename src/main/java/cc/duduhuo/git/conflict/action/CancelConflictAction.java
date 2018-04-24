package cc.duduhuo.git.conflict.action;

import cc.duduhuo.git.conflict.Global;
import cc.duduhuo.git.conflict.InDocumentListener;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.project.Project;

import static cc.duduhuo.git.conflict.Global.sConflictItemMap;
import static cc.duduhuo.git.conflict.Global.sIsHighlightMap;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 20:57
 * Description:
 * Remarks:
 * =======================================================
 */
public class CancelConflictAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Document document = editor.getDocument();
        sIsHighlightMap.put(editor, false);
        sConflictItemMap.remove(document);
        final MarkupModel markupModel = editor.getMarkupModel();
        markupModel.removeAllHighlighters();
        // remove document listener
        InDocumentListener listener = Global.sDocumentListenerMap.get(document);
        if (listener != null) {
            document.removeDocumentListener(listener);
            Global.sDocumentListenerMap.remove(document);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        //Get required data keys
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        //Set visibility only in case of existing project and editor
        e.getPresentation().setVisible(false);
        boolean canShow = (project != null && editor != null);
        if (canShow) {
            Boolean isHighlight = sIsHighlightMap.getOrDefault(editor, false);
            if (isHighlight) {
                e.getPresentation().setVisible(true);
            }
        }
    }
}
