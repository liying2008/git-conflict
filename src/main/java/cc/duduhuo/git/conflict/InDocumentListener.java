package cc.duduhuo.git.conflict;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.markup.MarkupModel;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 10:37
 * Description:
 * Remarks:
 * =======================================================
 */
public final class InDocumentListener implements DocumentListener {
    private Document mDocument;
    private MarkupModel mMarkupModel;

    public InDocumentListener(Document document, MarkupModel markupModel) {
        this.mDocument = document;
        this.mMarkupModel = markupModel;
    }

    @Override
    public void documentChanged(DocumentEvent event) {
        DocumentTools.showConflict(mDocument, mMarkupModel);
    }
}

