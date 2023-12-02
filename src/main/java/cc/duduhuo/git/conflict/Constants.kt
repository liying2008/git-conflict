package cc.duduhuo.git.conflict

import com.intellij.openapi.editor.markup.HighlighterLayer

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 18:00
 * Description:
 * Remarks:
 * =======================================================
 */
object Constants {
    const val CURRENT_CHANGE_MARKER = "<<<<<<< "
    const val COMMON_MARKER = "||||||| "
    const val SEPARATOR_MARKER = "======="
    const val INCOMING_CHANGE_MARKER = ">>>>>>> "

    const val HIGHLIGHTER_LAYER = HighlighterLayer.SELECTION - 1

    object Resource {
        const val NOTIFICATION_GROUP_DISPLAY_ID = "Git Conflict"
        const val SETTINGS_TITLE = "Git Conflict"
    }
}
