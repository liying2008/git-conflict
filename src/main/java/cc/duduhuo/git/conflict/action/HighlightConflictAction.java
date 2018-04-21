package cc.duduhuo.git.conflict.action;

import cc.duduhuo.git.conflict.Constants;
import cc.duduhuo.git.conflict.Global;
import cc.duduhuo.git.conflict.TextAttr;
import cc.duduhuo.git.conflict.model.ConflictItem;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.colors.EditorFontType;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.ex.MarkupModelEx;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 9:53
 * Description:
 * Remarks:
 * =======================================================
 */
public class HighlightConflictAction extends AnAction {
    List<ConflictItem> mConflictItems = new ArrayList<>();

    @Override
    public void actionPerformed(AnActionEvent e) {
        Global.sIsHighlight = true;
        System.out.println("3 highlight = " + Global.sIsHighlight);
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getProject();

        //Access document, caret, and selection
        final Document document = editor.getDocument();
        final MarkupModel markupModel = editor.getMarkupModel();
        final CaretModel caretModel = editor.getCaretModel();
        showConflict(document, markupModel);
        document.addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(DocumentEvent event) {
                showConflict(document, markupModel);
            }
        });

    }

    private void showConflict(Document document, MarkupModel markupModel) {
        int lineCount = document.getLineCount();
        String text = document.getText();
        String[] textArr = text.split("\n");

        markupModel.removeAllHighlighters();

        int currentChangeLineNum = -1;
        int separatorLineNum = -1;
        int incomingLineNum = -1;

        for (int i = 0; i < lineCount; i++) {
            if (textArr[i].startsWith(Constants.CURRENT_CHANGE)) {
                currentChangeLineNum = i;
            } else if (textArr[i].startsWith(Constants.SEPARATOR)) {
                if (currentChangeLineNum > -1) {
                    separatorLineNum = i;
                }
            } else if (textArr[i].startsWith(Constants.INCOMING_CHANGE)) {
                if (separatorLineNum > -1) {
                    incomingLineNum = i;
                    mConflictItems.add(new ConflictItem(currentChangeLineNum, separatorLineNum, incomingLineNum));
                    markupModel.addLineHighlighter(currentChangeLineNum, Constants.HIGHLIGHTER_LAYER, TextAttr.sCurrentTitleAttributes);
                    for (int j = currentChangeLineNum + 1; j < separatorLineNum; j++) {
                        markupModel.addLineHighlighter(j, Constants.HIGHLIGHTER_LAYER, TextAttr.sCurrentAttributes);
                    }
                    for (int j = separatorLineNum + 1; j < incomingLineNum; j++) {
                        markupModel.addLineHighlighter(j, Constants.HIGHLIGHTER_LAYER, TextAttr.sIncomingAttributes);
                    }
                    markupModel.addLineHighlighter(incomingLineNum, Constants.HIGHLIGHTER_LAYER, TextAttr.sIncomingTitleAttributes);
                    currentChangeLineNum = -1;
                    separatorLineNum = -1;
                }
            }
        }
    }

    @Override
    public void update(AnActionEvent e) {
        System.out.println("1 highlight = " + Global.sIsHighlight);
        //Get required data keys
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        //Set visibility only in case of existing project and editor
        e.getPresentation().setVisible(false);
        boolean isShow = (project != null && editor != null);
        if (isShow) {
            if (!Global.sIsHighlight) {
                e.getPresentation().setVisible(true);
            }
        }
    }
}
