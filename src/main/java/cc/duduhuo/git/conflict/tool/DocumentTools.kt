package cc.duduhuo.git.conflict.tool

import cc.duduhuo.git.conflict.Constants
import cc.duduhuo.git.conflict.Global
import cc.duduhuo.git.conflict.TextAttr
import cc.duduhuo.git.conflict.model.ConflictItem
import cc.duduhuo.git.conflict.tool.EditorTools.removeConflictHighlightersIfAny
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.markup.RangeHighlighter
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
    const val SECTION_CURRENT = 0
    const val SECTION_COMMON = 1
    const val SECTION_INCOMING = 2

    private fun detectConflicts(text: String): List<ConflictItem> {
        val conflictItems = mutableListOf<ConflictItem>()

        val textArr = text.split("\n")

        var currentChangeMarkerLineNum = -1
        var commonMarkerLineNum = -1
        var separatorMarkerLineNum = -1
        var incomingChangeMarkerLineNum = -1

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
                    incomingChangeMarkerLineNum = index

                    // get conflict content
                    val currentChangeStartLineNum = currentChangeMarkerLineNum + 1
                    val currentChangeEndLineNum =
                        if (commonMarkerLineNum > -1) commonMarkerLineNum else separatorMarkerLineNum

                    val (commonStartLineNum, commonEndLineNum) = if (commonMarkerLineNum > -1) {
                        Pair(commonMarkerLineNum + 1, separatorMarkerLineNum)
                    } else {
                        Pair(-1, -1)
                    }

                    val incomingChangeStartLineNum = separatorMarkerLineNum + 1
                    val incomingChangeEndLineNum = incomingChangeMarkerLineNum

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
                    )

                    conflictItems.add(item)

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
        return conflictItems
    }

    /**
     * Gets the string of a conflict section in a document.
     *
     * @param document the document
     * @param item the conflict item
     * @param section the section of conflict, must be one of `SECTION_CURRENT`, `SECTION_COMMON`, or `SECTION_INCOMING`
     * @return the string of the conflict section
     */
    fun getConflictSectionString(document: Document, item: ConflictItem, section: Int): String {
        return when (section) {
            SECTION_CURRENT -> {
                val currentStartOffset = document.getLineStartOffset(item.currentChangeStartLineNum)
                val currentEndOffset = document.getLineStartOffset(item.currentChangeEndLineNum)
                if (currentEndOffset > currentStartOffset) {
                    // current change string
                    document.getText(TextRange(currentStartOffset, currentEndOffset))
                } else {
                    ""
                }
            }

            SECTION_COMMON -> {
                if (item.commonMarkerLineNum > -1) {
                    val commonStartOffset = document.getLineStartOffset(item.commonStartLineNum)
                    val commonEndOffset = document.getLineStartOffset(item.commonEndLineNum)
                    if (commonEndOffset > commonStartOffset) {
                        // common change string
                        document.getText(TextRange(commonStartOffset, commonEndOffset))
                    } else {
                        ""
                    }
                } else {
                    ""
                }
            }

            SECTION_INCOMING -> {
                val incomingStartOffset = document.getLineStartOffset(item.incomingChangeStartLineNum)
                val incomingEndOffset = document.getLineStartOffset(item.incomingChangeEndLineNum)
                if (incomingEndOffset > incomingStartOffset) {
                    // incoming change string
                    document.getText(TextRange(incomingStartOffset, incomingEndOffset))
                } else {
                    ""
                }
            }

            else -> {
                // unreachable
                ""
            }
        }

    }

    /**
     * Displays conflicts in document.
     *
     * @param editor
     * @return number of conflicts
     */
    fun showConflict(editor: Editor): Int {
        val document = editor.document
        val text = document.text

        val conflictItems = detectConflicts(text)

        editor.removeConflictHighlightersIfAny()
        Global.conflictItemMap.remove(document)

        if (conflictItems.isEmpty()) {
            return 0
        }

        val currentHeaderAttributes = TextAttr.getCurrentHeaderAttributes()
        val currentContentAttributes = TextAttr.getCurrentContentAttributes()
        val commonHeaderAttributes = TextAttr.getCommonHeaderAttributes()
        val commonContentAttributes = TextAttr.getCommonContentAttributes()
        val incomingHeaderAttributes = TextAttr.getIncomingHeaderAttributes()
        val incomingContentAttributes = TextAttr.getIncomingContentAttributes()

        // new highlighters in editor
        val highlighters = mutableListOf<RangeHighlighter>()

        val markupModel = editor.markupModel

        conflictItems.forEach {
            highlighters.add(
                markupModel.addLineHighlighter(
                    it.currentChangeMarkerLineNum,
                    Constants.HIGHLIGHTER_LAYER,
                    currentHeaderAttributes
                )
            )
            for (j in it.currentChangeStartLineNum until it.currentChangeEndLineNum) {
                highlighters.add(
                    markupModel.addLineHighlighter(
                        j,
                        Constants.HIGHLIGHTER_LAYER,
                        currentContentAttributes
                    )
                )
            }
            if (it.commonMarkerLineNum > -1) {
                highlighters.add(
                    markupModel.addLineHighlighter(
                        it.commonMarkerLineNum,
                        Constants.HIGHLIGHTER_LAYER,
                        commonHeaderAttributes
                    )
                )
                for (j in it.commonStartLineNum until it.commonEndLineNum) {
                    highlighters.add(
                        markupModel.addLineHighlighter(
                            j,
                            Constants.HIGHLIGHTER_LAYER,
                            commonContentAttributes
                        )
                    )
                }
            }

            for (j in it.incomingChangeStartLineNum until it.incomingChangeEndLineNum) {
                highlighters.add(
                    markupModel.addLineHighlighter(
                        j,
                        Constants.HIGHLIGHTER_LAYER,
                        incomingContentAttributes
                    )
                )
            }
            highlighters.add(
                markupModel.addLineHighlighter(
                    it.incomingChangeMarkerLineNum,
                    Constants.HIGHLIGHTER_LAYER,
                    incomingHeaderAttributes
                )
            )
        }

        Global.highlighterMap[editor] = highlighters
        Global.conflictItemMap[document] = conflictItems
        return conflictItems.size
    }
}
