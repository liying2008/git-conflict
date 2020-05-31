package cc.duduhuo.git.conflict

import cc.duduhuo.git.conflict.model.ConflictItem
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 20:59
 * Description:
 * Remarks:
 * =======================================================
 */
object Global {
    // is it highlighted
    var sIsHighlightMap: MutableMap<Editor, Boolean> = mutableMapOf()

    // conflict item list
    var sConflictItemMap: MutableMap<Document, MutableList<ConflictItem>> = mutableMapOf()

    var sDocumentListenerMap: MutableMap<Document, InDocumentListener> = mutableMapOf()

    // current color scheme
    var sCurrentColor = GlobalSettings.getPersistentState().markColors[GlobalSettings.getPersistentState().schemeName]
}
