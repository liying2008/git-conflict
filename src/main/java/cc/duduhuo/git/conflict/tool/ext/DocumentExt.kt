package cc.duduhuo.git.conflict.tool.ext

import cc.duduhuo.git.conflict.Global
import cc.duduhuo.git.conflict.InDocumentListener
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor


/**
 * Removes the [cc.duduhuo.git.conflict.InDocumentListener] from the document
 * if the document has one.
 *
 * This method is usually called when the document is no longer needed to be
 * monitored.
 */
fun Document.removeInDocumentListenerIfExist() {
    val listener = Global.documentListenerMap[this] ?: return
    this.removeDocumentListener(listener)
    Global.documentListenerMap.remove(this)
}

/**
 * Adds a [cc.duduhuo.git.conflict.InDocumentListener] to the document
 * if the document does not already have a listener.
 *
 * If the document already has a listener, this method does nothing.
 *
 * @param editor the editor that owns the document
 */
fun Document.addInDocumentListenerIfNot(editor: Editor) {
    val oldListener = Global.documentListenerMap[this]
    if (oldListener != null) {
        // listener already exists, do nothing
        return
    }
    val listener = InDocumentListener(editor)
    this.addDocumentListener(listener)
    Global.documentListenerMap[this] = listener
}
