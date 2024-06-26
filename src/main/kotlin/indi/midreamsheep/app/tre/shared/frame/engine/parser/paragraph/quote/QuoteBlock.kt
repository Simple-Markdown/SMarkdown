package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.quote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContextComposition
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TRELinePreButton
import indi.midreamsheep.app.tre.shared.ui.engine.editor.treEditorWithoutScroll

class QuoteBlock(
    private val blockManager: TREBlockManager
):TRETextBlock {

    override fun getTextFieldValue(): TextFieldValue {
        TODO("Not yet implemented")
    }

    override fun setTextFieldValue(value: TextFieldValue) {
        TODO("Not yet implemented")
    }

    override fun isStart(): Boolean {
        val currentBlock = blockManager.getCurrentBlock()
        if (blockManager.getCurrentBlockIndex()!=0||currentBlock !is TRETextBlock) {
            return false
        }
        return currentBlock.isStart()
    }

    override fun isEnd(): Boolean {
        val currentBlock = blockManager.getCurrentBlock()
        if (blockManager.getCurrentBlockIndex()!=blockManager.getSize()-1||currentBlock !is TRETextBlock) {
            return false
        }
        return currentBlock.isEnd()
    }

    override fun focusFromLast() {
        val treBlock = blockManager.getTREBlock(blockManager.getSize()-1)
        if (treBlock is TRETextBlock){
            treBlock.focusFromLast()
            return
        }
        treBlock.focus()
    }

    override fun focusFromStart() {
        val treBlock = blockManager.getTREBlock(0)
        if (treBlock is TRETextBlock){
            treBlock.focusFromStart()
            return
        }
        treBlock.focus()
    }

    override fun focusTransform(transformPosition: Int) {}

    override fun focus() {
    }

    /**
     * 释放焦点
     * */
    override fun releaseFocus() {
        //blockManager.setCurrentBlockState(null)
    }

    /**
     * 获取当前的composable
     * */
    override fun getDisplay() = Display {
        {
            Row(Modifier.fillMaxWidth().background(Color(0xFF2D2D2))) {
                var height by remember { mutableStateOf(20.dp) }
                Box(Modifier.height(height).width(5.dp).background(Color.Green))
                Spacer(Modifier.width(3.dp))
                Box(Modifier.weight(1f).onGloballyPositioned { height = it.size.height.dp } ){
                    CompositionLocalProvider(getEditorContextComposition() provides blockManager.getContext()){
                        treEditorWithoutScroll()
                    }
                }
            }
        }
    }

    /**
     * 获取前置按钮
     */
    override fun getPreButton() = TRELinePreButton{ Display { {} }}

    /**
     * 获取当前的内容
     * */
    override fun getContent(): String {
        TODO("Not yet implemented")
    }

    override fun getBlockManager() = blockManager

    /**
     * 当block被加入manager时调用
     * */
    override fun whenInsert() {}

    /**
     * 当block被移除manager时调用
     * */
    override fun whenRemove() {}
}