package cc.duduhuo.git.conflict;

import cc.duduhuo.git.conflict.model.ConflictItem;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.util.TextRange;

import java.util.ArrayList;
import java.util.List;

import static cc.duduhuo.git.conflict.Global.sConflictItemMap;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 10:51
 * Description:
 * Remarks:
 * =======================================================
 */
public final class DocumentTools {
    public static void showConflict(final Document document, final MarkupModel markupModel) {
        String text = document.getText();
        String[] textArr = text.split("\n");
        int lineCount = textArr.length;

        markupModel.removeAllHighlighters();
        sConflictItemMap.remove(document);

        int currentChangeLineNum = -1;
        int separatorLineNum = -1;
        int incomingLineNum = -1;
        List<ConflictItem> conflictItems = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            if (textArr[i].startsWith(Constants.CURRENT_CHANGE)) {
                currentChangeLineNum = i;
                separatorLineNum = -1;
                incomingLineNum = -1;
            } else if (textArr[i].startsWith(Constants.SEPARATOR)) {
                if (currentChangeLineNum > -1 && separatorLineNum == -1) {
                    separatorLineNum = i;
                } else {
                    currentChangeLineNum = -1;
                    separatorLineNum = -1;
                }
            } else if (textArr[i].startsWith(Constants.INCOMING_CHANGE)) {
                if (separatorLineNum > -1) {
                    incomingLineNum = i;
                    // get conflict content
                    int currentStartOffset = document.getLineStartOffset(currentChangeLineNum + 1);
                    int currentEndOffset = document.getLineEndOffset(separatorLineNum - 1);
                    String currentChangeStr = "";
                    if (currentEndOffset > currentStartOffset) {
                        currentChangeStr = document.getText(new TextRange(currentStartOffset, currentEndOffset));
                    }
                    int incomingStartOffset = document.getLineStartOffset(separatorLineNum + 1);
                    int incomingEndOffset = document.getLineEndOffset(incomingLineNum - 1);
                    String incomingChangeStr = "";
                    if (incomingEndOffset > incomingStartOffset) {
                        incomingChangeStr = document.getText(new TextRange(incomingStartOffset, incomingEndOffset));
                    }

                    ConflictItem item = new ConflictItem(currentChangeLineNum, separatorLineNum, incomingLineNum);
                    item.setCurrentChangeStr(currentChangeStr);
                    item.setIncomingChangeStr(incomingChangeStr);
                    conflictItems.add(item);

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
        sConflictItemMap.put(document, conflictItems);
    }
}
