package cc.duduhuo.git.conflict;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 10:37
 * Description:
 * Remarks:
 * =======================================================
 */
public final class InDocumentListener implements DocumentListener {
    private Editor mEditor;

    public InDocumentListener(Editor editor) {
        this.mEditor = editor;
    }

    @Override
    public void documentChanged(DocumentEvent event) {
        DocumentTools.showConflict(mEditor);
    }
}

