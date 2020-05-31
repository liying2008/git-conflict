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
    const val CURRENT_CHANGE = "<<<<<<< "
    const val SEPARATOR = "======="
    const val INCOMING_CHANGE = ">>>>>>> "

    const val HIGHLIGHTER_LAYER = HighlighterLayer.SELECTION - 1

    object BundleKey {
        const val GROUP_DISPLAY_ID = "notification.group.display.id"
        const val SETTINGS_TITLE = "settings.title"
    }
}
