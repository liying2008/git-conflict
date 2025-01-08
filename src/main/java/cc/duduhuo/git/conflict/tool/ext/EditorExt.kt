package cc.duduhuo.git.conflict.tool.ext

import cc.duduhuo.git.conflict.Global
import com.intellij.openapi.editor.Editor
import kotlin.collections.isNotEmpty

/**
 * Removes all conflict highlighters from this editor if they exist.
 */
fun Editor.removeConflictHighlightersIfAny() {
    val highlighters = Global.highlighterMap[this] ?: return
    if (highlighters.isNotEmpty()) {
        this.markupModel.removeHighlighters(highlighters)
    }
    Global.highlighterMap.remove(this)
}
