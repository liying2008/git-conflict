package cc.duduhuo.git.conflict.tool.ext

import com.intellij.openapi.editor.markup.MarkupModel
import com.intellij.openapi.editor.markup.RangeHighlighter

/**
 * Removes a collection of range highlighters from the markup model.
 *
 * @param highlighters A collection of RangeHighlighter objects to be removed.
 */
fun MarkupModel.removeHighlighters(highlighters: Collection<RangeHighlighter>) {
    for (highlighter in highlighters) {
        removeHighlighter(highlighter)
    }
}
