package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.quote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TREBlock
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContextComposition
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TRELinePreButton
import indi.midreamsheep.app.tre.shared.ui.engine.editor.treEditorWithoutScroll

class QuoteBlock(
    private val blockManager: TREBlockManager
):TREBlock {

    override fun focus() {}

    /**
     * 释放焦点
     * */
    override fun releaseFocus() {}

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