package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockDisplay
import indi.midreamsheep.app.tre.shared.frame.engine.block.context.TREContextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREFocusEnum
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContextComposition
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TREDefaultLinePreButton
import indi.midreamsheep.app.tre.shared.ui.engine.editor.treEditorWithoutScroll

class ListBlock(
    blockManager: TREBlockManager,
    val listType: ListType,
    val listContext: TREEditorContext
): TREContextBlock(blockManager) {

    private val quoteBlockDisplay = object : TREBlockDisplay {
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

    override fun focusInStart() {
        listContext.blockManager.getTREBlock(0).focus(TREFocusEnum.IN_START.id)
        setCurrentBlock(0)
    }

    override fun focusInEnd() {
        listContext.blockManager.getTREBlock(listContext.blockManager.getSize()-1).focus(TREFocusEnum.IN_END.id)
        setCurrentBlock(listContext.blockManager.getSize()-1)
    }

    override fun focusStandard() {
        listContext.blockManager.getTREBlock(0).focus(TREFocusEnum.STANDARD.id)
        setCurrentBlock(0)
    }

    override fun inTargetPositionDown(xPositionData: XPositionData) {
        listContext.blockManager.getTREBlock(0).focus(TREFocusEnum.IN_TARGET_POSITION_UP.id,xPositionData)
        setCurrentBlock(0)
    }

    override fun inTargetPositionUp(xPositionData: XPositionData) {
        listContext.blockManager.getTREBlock(listContext.blockManager.getSize()-1).focus(TREFocusEnum.IN_TARGET_POSITION_DOWN.id,xPositionData)
        setCurrentBlock(listContext.blockManager.getSize()-1)
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