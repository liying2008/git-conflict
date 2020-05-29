package cc.duduhuo.git.conflict;

import cc.duduhuo.git.conflict.tool.DocumentTools;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import org.jetbrains.annotations.NotNull;

import static cc.duduhuo.git.conflict.Global.sConflictItemMap;
import static cc.duduhuo.git.conflict.Global.sIsHighlightMap;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 10:37
 * Description:
 * Remarks:
 * =======================================================
 */
public final class InDocumentListener implements DocumentListener {
    private final Editor mEditor;

    public InDocumentListener(Editor editor) {
        this.mEditor = editor;
    }

    @Override
    public void documentChanged(@NotNull DocumentEvent event) {
        boolean hasConflict = DocumentTools.showConflict(mEditor);
        if (!hasConflict) {
            Document document = mEditor.getDocument();
            sIsHighlightMap.put(mEditor, false);
            sConflictItemMap.remove(document);
            InDocumentListener listener = Global.sDocumentListenerMap.get(document);
            if (listener != null) {
                document.removeDocumentListener(listener);
                Global.sDocumentListenerMap.remove(document);
            }
        }
    }
}

