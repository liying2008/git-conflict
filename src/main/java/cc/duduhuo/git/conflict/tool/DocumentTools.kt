package cc.duduhuo.git.conflict.tool

import cc.duduhuo.git.conflict.Constants
import cc.duduhuo.git.conflict.Global
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
        markupModel.removeAllHighlighters()
        Global.conflictItemMap.remove(document)
        var currentChangeMarkerLineNum = -1
        var commonMarkerLineNum = -1
        var separatorMarkerLineNum = -1
        var incomingChangeMarkerLineNum = -1
        val conflictItems = mutableListOf<ConflictItem>()
        textArr.forEachIndexed { index, line ->
            if (line.startsWith(Constants.CURRENT_CHANGE_MARKER)) {
                currentChangeMarkerLineNum = index
                commonMarkerLineNum = -1
                separatorMarkerLineNum = -1
                incomingChangeMarkerLineNum = -1
            } else if (line.startsWith(Constants.COMMON_MARKER)) {
                if (currentChangeMarkerLineNum > -1 && commonMarkerLineNum == -1 && separatorMarkerLineNum == -1) {
                    commonMarkerLineNum = index
                } else {
                    // no op
                }
            } else if (line.startsWith(Constants.SEPARATOR_MARKER)) {
                if (currentChangeMarkerLineNum > -1 && separatorMarkerLineNum == -1) {
                    separatorMarkerLineNum = index
                } else {
                    currentChangeMarkerLineNum = -1
                    separatorMarkerLineNum = -1
                }
            } else if (line.startsWith(Constants.INCOMING_CHANGE_MARKER)) {
                if (currentChangeMarkerLineNum > -1 && separatorMarkerLineNum > -1) {
                    hasConflict = true
                    incomingChangeMarkerLineNum = index

                    // get conflict content
                    val currentChangeStartLineNum = currentChangeMarkerLineNum + 1
                    val currentChangeEndLineNum =
                        if (commonMarkerLineNum > -1) commonMarkerLineNum else separatorMarkerLineNum

                    val currentStartOffset = document.getLineStartOffset(currentChangeStartLineNum)
                    val currentEndOffset = document.getLineStartOffset(currentChangeEndLineNum)
                    var currentChangeStr = ""
                    if (currentEndOffset > currentStartOffset) {
                        currentChangeStr = document.getText(TextRange(currentStartOffset, currentEndOffset))
                    }

                    val (commonStartLineNum, commonEndLineNum) = if (commonMarkerLineNum > -1) {
                        Pair(commonMarkerLineNum + 1, separatorMarkerLineNum)
                    } else {
                        Pair(-1, -1)
                    }
                    val commonStr = if (commonMarkerLineNum > -1) {
                        val commonStartOffset = document.getLineStartOffset(commonStartLineNum)
                        val commonEndOffset = document.getLineStartOffset(commonEndLineNum)
                        if (commonEndOffset > commonStartOffset) {
                            document.getText(TextRange(commonStartOffset, commonEndOffset))
                        } else {
                            ""
                        }
                    } else {
                        ""
                    }

                    val incomingChangeStartLineNum = separatorMarkerLineNum + 1
                    val incomingChangeEndLineNum = incomingChangeMarkerLineNum
                    val incomingStartOffset = document.getLineStartOffset(incomingChangeStartLineNum)
                    val incomingEndOffset = document.getLineStartOffset(incomingChangeEndLineNum)
                    var incomingChangeStr = ""
                    if (incomingEndOffset > incomingStartOffset) {
                        incomingChangeStr = document.getText(TextRange(incomingStartOffset, incomingEndOffset))
                    }

                    val item = ConflictItem(
                        currentChangeMarkerLineNum = currentChangeMarkerLineNum,
                        currentChangeStartLineNum = currentChangeStartLineNum,
                        currentChangeEndLineNum = currentChangeEndLineNum,
                        commonMarkerLineNum = commonMarkerLineNum,
                        commonStartLineNum = commonStartLineNum,
                        commonEndLineNum = commonEndLineNum,
                        separatorMarkerLineNum = separatorMarkerLineNum,
                        incomingChangeMarkerLineNum = incomingChangeMarkerLineNum,
                        incomingChangeStartLineNum = incomingChangeStartLineNum,
                        incomingChangeEndLineNum = incomingChangeEndLineNum,
                        currentChangeStr = currentChangeStr,
                        commonStr = commonStr,
                        incomingChangeStr = incomingChangeStr,
                    )

                    conflictItems.add(item)

                    markupModel.addLineHighlighter(
                        currentChangeMarkerLineNum,
                        Constants.HIGHLIGHTER_LAYER,
                        TextAttr.currentHeaderAttributes
                    )
                    for (j in currentChangeStartLineNum until currentChangeEndLineNum) {
                        markupModel.addLineHighlighter(
                            j,
                            Constants.HIGHLIGHTER_LAYER,
                            TextAttr.currentContentAttributes
                        )
                    }
                    if (commonMarkerLineNum > -1) {
                        markupModel.addLineHighlighter(
                            commonMarkerLineNum,
                            Constants.HIGHLIGHTER_LAYER,
                            TextAttr.commonHeaderAttributes
                        )
                        for (j in commonStartLineNum until commonEndLineNum) {
                            markupModel.addLineHighlighter(
                                j,
                                Constants.HIGHLIGHTER_LAYER,
                                TextAttr.commonContentAttributes
                            )
                        }
                    }

                    for (j in incomingChangeStartLineNum until incomingChangeEndLineNum) {
                        markupModel.addLineHighlighter(
                            j,
                            Constants.HIGHLIGHTER_LAYER,
                            TextAttr.incomingContentAttributes
                        )
                    }
                    markupModel.addLineHighlighter(
                        incomingChangeMarkerLineNum,
                        Constants.HIGHLIGHTER_LAYER,
                        TextAttr.incomingHeaderAttributes
                    )

                    // reset marker line num
                    currentChangeMarkerLineNum = -1
                    commonMarkerLineNum = -1
                    separatorMarkerLineNum = -1
                } else {
                    // reset marker line num
                    currentChangeMarkerLineNum = -1
                    commonMarkerLineNum = -1
                    separatorMarkerLineNum = -1
                    incomingChangeMarkerLineNum = -1
                }
            }
        }
        Global.conflictItemMap[document] = conflictItems
        return hasConflict
    }
}
