package cc.duduhuo.git.conflict.action.operation;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 20:33
 * Description:
 * Remarks:
 * =======================================================
 */
public class AcceptCurrentChangeAction extends AbsFixConflict {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getProject();
        fixConflict(editor, project, AbsFixConflict.ACCEPT_CURRENT);
    }
}
