package cc.duduhuo.git.conflict.tool

import cc.duduhuo.git.conflict.Constants
import cc.duduhuo.git.conflict.Global.sConflictItemMap
import cc.duduhuo.git.conflict.TextAttr
import cc.duduhuo.git.conflict.model.ConflictItem
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 10:51
 * Description:
 * Remarks:
 * =======================================================
 */
object DocumentTools {
    /**
     * Displays conflicts in document.
     *
     * @param editor
     * @return false: no conflicts; true: there is(are) conflict(s)
     */
    fun showConflict(editor: Editor): Boolean {
        var hasConflict = false
        val document = editor.document
        val markupModel = editor.markupModel
        val text = document.text
        val textArr = text.split("\n")
        val lineCount = textArr.size
        markupModel.removeAllHighlighters()
        sConflictItemMap.remove(document)
        var currentChangeLineNum = -1
        var separatorLineNum = -1
        var incomingLineNum = -1
        val conflictItems: MutableList<ConflictItem> = mutableListOf()
        for (i in 0 until lineCount) {
            if (textArr[i].startsWith(Constants.CURRENT_CHANGE)) {
                currentChangeLineNum = i
                separatorLineNum = -1
                incomingLineNum = -1
            } else if (textArr[i].startsWith(Constants.SEPARATOR)) {
                if (currentChangeLineNum > -1 && separatorLineNum == -1) {
                    separatorLineNum = i
                } else {
                    currentChangeLineNum = -1
                    separatorLineNum = -1
                }
            } else if (textArr[i].startsWith(Constants.INCOMING_CHANGE)) {
                if (separatorLineNum > -1) {
                    hasConflict = true
                    incomingLineNum = i
                    // get conflict content
                    val currentStartOffset = document.getLineStartOffset(currentChangeLineNum + 1)
                    val currentEndOffset = document.getLineEndOffset(separatorLineNum - 1)
                    var currentChangeStr = ""
                    if (currentEndOffset > currentStartOffset) {
                        currentChangeStr = document.getText(TextRange(currentStartOffset, currentEndOffset))
                    }
                    val incomingStartOffset = document.getLineStartOffset(separatorLineNum + 1)
                    val incomingEndOffset = document.getLineEndOffset(incomingLineNum - 1)
                    var incomingChangeStr = ""
                    if (incomingEndOffset > incomingStartOffset) {
                        incomingChangeStr = document.getText(TextRange(incomingStartOffset, incomingEndOffset))
                    }
                    val item = ConflictItem(
                        currentChangeLineNum,
                        separatorLineNum,
                        incomingLineNum
                    )
                    item.currentChangeStr = currentChangeStr
                    item.incomingChangeStr = incomingChangeStr
                    conflictItems.add(item)
                    markupModel.addLineHighlighter(
                        currentChangeLineNum,
                        Constants.HIGHLIGHTER_LAYER,
                        TextAttr.sCurrentTitleAttributes
                    )
                    for (j in currentChangeLineNum + 1 until separatorLineNum) {
                        markupModel.addLineHighlighter(
                            j,
                            Constants.HIGHLIGHTER_LAYER,
                            TextAttr.sCurrentAttributes
                        )
                    }
                    for (j in separatorLineNum + 1 until incomingLineNum) {
                        markupModel.addLineHighlighter(
                            j,
                            Constants.HIGHLIGHTER_LAYER,
                            TextAttr.sIncomingAttributes
                        )
                    }
                    markupModel.addLineHighlighter(
                        incomingLineNum,
                        Constants.HIGHLIGHTER_LAYER,
                        TextAttr.sIncomingTitleAttributes
                    )
                    currentChangeLineNum = -1
                    separatorLineNum = -1
                }
            }
        }
        sConflictItemMap[document] = conflictItems
        return hasConflict
    }
}