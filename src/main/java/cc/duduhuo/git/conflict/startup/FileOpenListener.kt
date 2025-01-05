package cc.duduhuo.git.conflict.startup

import cc.duduhuo.git.conflict.Global
import cc.duduhuo.git.conflict.InDocumentListener
import cc.duduhuo.git.conflict.tool.DocumentTools
import cc.duduhuo.git.conflict.tool.NotificationTools.showNotification
import com.intellij.largeFilesEditor.editor.LargeFileEditor
import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.ex.FileEditorWithProvider
import com.intellij.openapi.vfs.VirtualFile

class FileOpenListener : FileEditorManagerListener {

    // fileOpenedSync 是同步触发的，在文件被打开并且在 IDE 内部完成了所有相关的处理（如文件加载、初始化等）之后立即被调用。
    override fun fileOpenedSync(
        source: FileEditorManager,
        file: VirtualFile,
        editorsWithProviders: MutableList<FileEditorWithProvider>
    ) {
        super.fileOpenedSync(source, file, editorsWithProviders)
        if (file.fileType.isBinary) {
            return
        }
        val editor = source.getSelectedEditor(file) ?: return
        // println("open:selectedEditor: ${editor.file.url}")
        if (editor is LargeFileEditor) {
            // is a large file
            // LargeFileEditor is not a TextEditor
            showNotification(
                editor.file.name,
                "File too large and does not perform conflict detection.",
                NotificationType.INFORMATION,
                source.project,
                listOf(
                    object : NotificationAction("Open file") {
                        override fun actionPerformed(e: AnActionEvent, notification: Notification) {
                            source.openFile(editor.file, true)
                            notification.expire()  // close notification
                        }
                    }
                )
            )
            return
        }
        if (editor !is TextEditor) {
            // it is not a text editor
            return
        }

        val conflictsCount = DocumentTools.showConflict(editor.editor)
        if (conflictsCount == 0) {
            // no conflicts detected
            return
        }
        showNotification(
            editor.file.name,
            "The document has $conflictsCount conflict(s).",
            NotificationType.WARNING,
            source.project,
            listOf(
                object : NotificationAction("Open file") {
                    override fun actionPerformed(e: AnActionEvent, notification: Notification) {
                        source.openFile(editor.file, true)
                        notification.expire()  // close notification
                    }
                }
            )
        )
        Global.isHighlightMap[editor.editor] = true
        val document = editor.editor.document
        val oldListener = Global.documentListenerMap[document]
        if (oldListener == null) {
            val documentListener = InDocumentListener(editor.editor)
            document.addDocumentListener(documentListener)
            Global.documentListenerMap[document] = documentListener
        }
    }
}
