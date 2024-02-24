package indi.midreamsheep.app.tre.context.editor.action

import indi.midreamsheep.app.tre.context.TREAction
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor

class ClipboardAction(context: TREEditorContext) : TREAction<TREEditorContext>(context) {
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