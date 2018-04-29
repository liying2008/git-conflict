package cc.duduhuo.git.conflict.action;

import cc.duduhuo.git.conflict.Global;
import cc.duduhuo.git.conflict.InDocumentListener;
import cc.duduhuo.git.conflict.tool.DocumentTools;
import cc.duduhuo.git.conflict.tool.NotificationTools;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

import java.util.Set;

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

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);

        final Document document = editor.getDocument();
        boolean hasConflict = DocumentTools.showConflict(editor);
        if (!hasConflict) {
            NotificationTools.showNotification("No Git Conflict", "There is no conflict in the document.",
                NotificationType.INFORMATION);
            return;
        }

        sIsHighlightMap.put(editor, true);
        InDocumentListener oldListener = sDocumentListenerMap.get(document);
        if (oldListener == null) {
            InDocumentListener documentListener = new InDocumentListener(editor);
            document.addDocumentListener(documentListener);
            Global.sDocumentListenerMap.put(document, documentListener);
        }
    }

    public static void refreshHighlight() {
        Set<Editor> keySet = Global.sIsHighlightMap.keySet();
        for (Editor editor : keySet) {
            if (Global.sIsHighlightMap.get(editor)) {
                DocumentTools.showConflict(editor);
            }
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
            Boolean isHighlight = sIsHighlightMap.getOrDefault(editor, false);
            //Set visibility only in case of existing project and editor
            if (!isHighlight) {
                e.getPresentation().setVisible(true);
            }
        }
    }
}
