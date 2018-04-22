package cc.duduhuo.git.conflict;

import com.intellij.openapi.editor.markup.HighlighterLayer;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 18:00
 * Description:
 * Remarks:
 * =======================================================
 */
public interface Constants {
    String CURRENT_CHANGE = "<<<<<<< HEAD";
    String SEPARATOR = "=======";
    String INCOMING_CHANGE = ">>>>>>> ";

    int HIGHLIGHTER_LAYER = HighlighterLayer.SELECTION - 1;

    interface BundleKey {
        String GROUP_DISPLAY_ID = "notification.group.display.id";
        String SETTINGS_TITLE = "settings.title";
    }
}
