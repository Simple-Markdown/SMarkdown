package indi.midreamsheep.app.tre.desktop.page.editor.context.action

import indi.midreamsheep.app.tre.desktop.context.TREAction
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor

class ClipboardAction(context: TREEditorWindowContext) : TREAction<TREEditorWindowContext>(context) {
    fun getClipboardContentType(): String {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val transferable = clipboard.getContents(null)
        return when {
            transferable.isDataFlavorSupported(DataFlavor.stringFlavor) -> "Text"
            transferable.isDataFlavorSupported(DataFlavor.imageFlavor) -> "Image"
            else -> "Unknown"
        }
    }
    fun getClipboardContent(): String {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val transferable = clipboard.getContents(null)
        return when {
            transferable.isDataFlavorSupported(DataFlavor.stringFlavor) -> transferable.getTransferData(DataFlavor.stringFlavor) as String
            else -> ""
        }
    }
}