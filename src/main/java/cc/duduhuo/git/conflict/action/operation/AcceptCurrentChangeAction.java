package cc.duduhuo.git.conflict.action.operation;

import cc.duduhuo.git.conflict.Constants;
import cc.duduhuo.git.conflict.Global;
import cc.duduhuo.git.conflict.model.ConflictItem;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

import java.util.List;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 20:33
 * Description:
 * Remarks:
 * =======================================================
 */
public class AcceptCurrentChangeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getProject();

        final Document document = editor.getDocument();

        if (document.isWritable()) {
            final CaretModel caretModel = editor.getCaretModel();
            int offset = caretModel.getOffset();
            int lineNumber = document.getLineNumber(offset);

            List<ConflictItem> conflictItems = Global.sConflictItemMap.get(document);
            for (ConflictItem item : conflictItems) {
                if (item.getCurrentChangeLineNum() <= lineNumber && item.getIncomingLineNum() >= lineNumber) {
                    int start = document.getLineStartOffset(item.getCurrentChangeLineNum());
                    int end = document.getLineEndOffset(item.getIncomingLineNum());
                    String replaceStr = item.getCurrentChangeStr();
                    WriteCommandAction.runWriteCommandAction(project, () ->
                        document.replaceString(start, end, replaceStr)
                    );
                }
            }
        } else {
            Notification notification = new Notification(Constants.GROUP_DISPLAY_ID, "Fix Git Conflict", "This document can not be written.", NotificationType.WARNING);
            Notifications.Bus.notify(notification);
        }
    }
}
