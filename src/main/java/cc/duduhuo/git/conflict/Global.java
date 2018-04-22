package cc.duduhuo.git.conflict;

import cc.duduhuo.git.conflict.model.ConflictItem;
import com.intellij.openapi.editor.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 20:59
 * Description:
 * Remarks:
 * =======================================================
 */
public final class Global {
    // Is it highlighted
    public static Map<Document, Boolean> sIsHighlightMap = new HashMap<>();
    // conflict item list
    public static Map<Document, List<ConflictItem>> sConflictItemMap = new HashMap<>();
    public static Map<Document, InDocumentListener> sDocumentListenerMap = new HashMap<>();
}
