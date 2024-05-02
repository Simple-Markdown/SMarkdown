package indi.midreamsheep.app.tre.desktop.page.editor.context.action

import indi.midreamsheep.app.tre.shared.api.context.TREAction
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
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