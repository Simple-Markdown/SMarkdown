package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.CustomData
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TREBlockDisplay
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TREContextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.customdata.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContextComposition
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts.BackspaceShortcut
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts.DirectionLeftShortcut
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts.DirectionRightShortcut
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts.DownShortcut
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TREDefaultLinePreButton
import indi.midreamsheep.app.tre.shared.tool.id.getIdFromPool
import indi.midreamsheep.app.tre.shared.ui.engine.editor.treEditorWithoutScroll

class ListBlock(
    blockManager: TREBlockManager,
    listType: ListType,
    val listContext: TREEditorContext
): TREContextBlock(blockManager) {

    private val quoteBlockDisplay = object : TREBlockDisplay{
        override fun getDisplay()= Display {
            {
                Row(Modifier.fillMaxWidth()) {
                    listType.getPrefix().getComposable().invoke()
                    Box(Modifier.weight(1f)){
                        CompositionLocalProvider(getEditorContextComposition() provides listContext){
                            treEditorWithoutScroll()
                        }
                    }
                }
            }
        }

        override fun getPreButton() = TREDefaultLinePreButton()

    }

    override fun focus(typeId: Long, data: CustomData) {
        when(typeId){
            getIdFromPool(XPositionData::class.java) -> {
                listContext.blockManager.getTREBlock(listContext.blockManager.getSize()-1).focus(typeId, data)
                setCurrentBlock(listContext.blockManager.getSize()-1)
            }
            getIdFromPool(DownShortcut::class.java) -> {
                listContext.blockManager.getTREBlock(0).focus(typeId,data)
                setCurrentBlock(0)
            }
            getIdFromPool(BackspaceShortcut::class.java) -> {
                listContext.blockManager.getTREBlock(listContext.blockManager.getSize()-1).focus(typeId, data)
                setCurrentBlock(listContext.blockManager.getSize()-1)
            }
            getIdFromPool(DirectionRightShortcut::class.java) -> {
                listContext.blockManager.getTREBlock(0).focus(typeId,data)
                setCurrentBlock(0)
            }
            getIdFromPool(DirectionLeftShortcut::class.java) -> {
                listContext.blockManager.getTREBlock(listContext.blockManager.getSize()-1).focus(typeId, data)
                setCurrentBlock(listContext.blockManager.getSize()-1)
            }
            else->{
                listContext.blockManager.getTREBlock(0).focus(typeId,data)
                setCurrentBlock(listContext.blockManager.getSize()-1)
            }
        }
    }

    private fun setCurrentBlock(index:Int){
        listContext.blockManager.setCurrentBlock(listContext.blockManager.getTREBlock(index))
    }

    override fun releaseFocus() {
        listContext.blockManager.getCurrentBlock()?.releaseFocus()
        listContext.blockManager.setCurrentBlock(null)
    }

    override fun getTREBlockDisplay() = quoteBlockDisplay


    override fun getContent(): String {
        TODO("Not yet implemented")
    }

    override fun whenInsert() {}

    override fun whenRemove() {}
}