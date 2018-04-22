package cc.duduhuo.git.conflict.action;

import cc.duduhuo.git.conflict.DocumentTools;
import cc.duduhuo.git.conflict.Global;
import cc.duduhuo.git.conflict.InDocumentListener;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.project.Project;

import static cc.duduhuo.git.conflict.Global.sDocumentListenerMap;
import static cc.duduhuo.git.conflict.Global.sIsHighlightMap;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 9:53
 * Description:
 * Remarks:
 * =======================================================
 */
public class HighlightConflictAction extends AnAction {
    private static Document sDocument;
    private static MarkupModel sMarkupModel;

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);

        final Document document = editor.getDocument();
        sIsHighlightMap.put(document, true);
        final MarkupModel markupModel = editor.getMarkupModel();
        sDocument = document;
        sMarkupModel = markupModel;
        DocumentTools.showConflict(document, markupModel);

        InDocumentListener oldListener = sDocumentListenerMap.get(document);
        if (oldListener == null) {
            InDocumentListener documentListener = new InDocumentListener(document, markupModel);
            document.addDocumentListener(documentListener);
            Global.sDocumentListenerMap.put(document, documentListener);
        }
    }

    public static void refreshHighlight() {
        if (sDocument != null && sMarkupModel != null && sIsHighlightMap.get(sDocument)) {
            DocumentTools.showConflict(sDocument, sMarkupModel);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        //Get required data keys
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setVisible(false);
        boolean canShow = (project != null && editor != null);
        if (canShow) {
            Document document = editor.getDocument();
            Boolean isHighlight = sIsHighlightMap.getOrDefault(document, false);
            //Set visibility only in case of existing project and editor
            if (!isHighlight) {
                e.getPresentation().setVisible(true);
            }
        }
    }
}
