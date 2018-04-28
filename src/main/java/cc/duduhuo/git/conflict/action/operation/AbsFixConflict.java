package cc.duduhuo.git.conflict.action.operation;

import android.support.annotation.IntDef;
import cc.duduhuo.git.conflict.Global;
import cc.duduhuo.git.conflict.model.ConflictItem;
import cc.duduhuo.git.conflict.tool.NotificationTools;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/24 23:13
 * Description:
 * Remarks:
 * =======================================================
 */
public abstract class AbsFixConflict extends AnAction {
    protected static final int ACCEPT_CURRENT = 0;
    protected static final int ACCEPT_INCOMING = 1;
    protected static final int ACCEPT_BOTH = 2;

    @IntDef({ACCEPT_CURRENT, ACCEPT_INCOMING, ACCEPT_BOTH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STRATEGY {
    }

    protected void fixConflict(Editor editor, Project project, @STRATEGY int strategy) {
        final Document document = editor.getDocument();

        if (document.isWritable()) {
            final CaretModel caretModel = editor.getCaretModel();
            int offset = caretModel.getOffset();
            int lineNumber = document.getLineNumber(offset);
            // Does the cursor fall in the area of conflict content
            boolean inConflictPosition = false;

            List<ConflictItem> conflictItems = Global.sConflictItemMap.get(document);
            for (ConflictItem item : conflictItems) {
                if (item.getCurrentChangeLineNum() <= lineNumber && item.getIncomingLineNum() >= lineNumber) {
                    inConflictPosition = true;
                    int start = document.getLineStartOffset(item.getCurrentChangeLineNum());
                    int end = document.getLineEndOffset(item.getIncomingLineNum());
                    final String replaceStr;
                    if (strategy == ACCEPT_CURRENT) {
                        replaceStr = item.getCurrentChangeStr();
                    } else if (strategy == ACCEPT_INCOMING) {
                        replaceStr = item.getIncomingChangeStr();
                    } else if (strategy == ACCEPT_BOTH) {
                        replaceStr = item.getCurrentChangeStr() + "\n" + item.getIncomingChangeStr();
                    } else {
                        replaceStr = "";
                    }
                    WriteCommandAction.runWriteCommandAction(project, () ->
                        document.replaceString(start, end, replaceStr)
                    );
                    break;
                }
            }
            if (!inConflictPosition) {
                JBPopupFactory.getInstance().createMessage("Conflict content is not detected in this location.")
                    .showInBestPositionFor(editor);
            }
        } else {
            NotificationTools.showNotification("Fix Git Conflict", "This document can not be written.",
                NotificationType.WARNING);
        }
    }
}
