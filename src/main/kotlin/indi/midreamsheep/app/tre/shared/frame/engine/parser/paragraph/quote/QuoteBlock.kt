package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.quote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.CustomData
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TREBlockDisplay
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TREContextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.customdata.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContextComposition
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts.*
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TREDefaultLinePreButton
import indi.midreamsheep.app.tre.shared.tool.id.getIdFromPool
import indi.midreamsheep.app.tre.shared.ui.engine.editor.treEditorWithoutScroll

class QuoteBlock(
    blockManager: TREBlockManager,
    val quoteContext: TREEditorContext
): TREContextBlock(blockManager) {

    private val quoteBlockDisplay = object : TREBlockDisplay{
        override fun getDisplay()=Display {
            {
                Row(Modifier.fillMaxWidth().background(Color(0xFF2D2D2))) {
                    var height by remember { mutableStateOf(20.dp) }
                    Box(Modifier.height(height).width(5.dp).background(Color.Green))
                    Spacer(Modifier.width(3.dp))
                    Box(Modifier.weight(1f).onGloballyPositioned { height = it.size.height.dp } ){
                        CompositionLocalProvider(getEditorContextComposition() provides quoteContext){
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
                quoteContext.blockManager.getTREBlock(quoteContext.blockManager.getSize()-1).focus(typeId, data)
                setCurrentBlock(quoteContext.blockManager.getSize()-1)
            }
            getIdFromPool(UpShortcut::class.java) -> {
                quoteContext.blockManager.getTREBlock(quoteContext.blockManager.getSize()-1).focus(typeId, data)
                setCurrentBlock(quoteContext.blockManager.getSize()-1)
            }
            getIdFromPool(DownShortcut::class.java) -> {
                quoteContext.blockManager.getTREBlock(0).focus(typeId,data)
                setCurrentBlock(0)
            }
            getIdFromPool(BackspaceShortcut::class.java) -> {
                quoteContext.blockManager.getTREBlock(quoteContext.blockManager.getSize()-1).focus(typeId, data)
                setCurrentBlock(quoteContext.blockManager.getSize()-1)
            }
            getIdFromPool(DirectionRightShortcut::class.java) -> {
                quoteContext.blockManager.getTREBlock(0).focus(typeId,data)
                setCurrentBlock(0)
            }
            getIdFromPool(DirectionLeftShortcut::class.java) -> {
                quoteContext.blockManager.getTREBlock(quoteContext.blockManager.getSize()-1).focus(typeId, data)
                setCurrentBlock(quoteContext.blockManager.getSize()-1)
            }
            else->{
                quoteContext.blockManager.getTREBlock(quoteContext.blockManager.getSize()-1).focus(typeId,data)
                setCurrentBlock(quoteContext.blockManager.getSize()-1)
            }
        }
    }

    private fun setCurrentBlock(index:Int){
        quoteContext.blockManager.setCurrentBlock(quoteContext.blockManager.getTREBlock(index))
    }

    override fun releaseFocus() {
        quoteContext.blockManager.getCurrentBlock()?.releaseFocus()
        quoteContext.blockManager.setCurrentBlock(null)
    }

    override fun getTREBlockDisplay() = quoteBlockDisplay


    override fun getContent(): String {
        TODO("Not yet implemented")
    }

    override fun whenInsert() {}

    override fun whenRemove() {}
}