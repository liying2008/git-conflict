package cc.duduhuo.git.conflict.tool

import cc.duduhuo.git.conflict.Global
import cc.duduhuo.git.conflict.tool.ext.removeHighlighters
import com.intellij.openapi.editor.Editor

object EditorTools {
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
}
