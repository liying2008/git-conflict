package cc.duduhuo.git.conflict.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

import static cc.duduhuo.git.conflict.Global.sIsHighlightMap;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 22:12
 * Description:
 * Remarks:
 * =======================================================
 */
public class ConflictOpActionGroup extends DefaultActionGroup {
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
