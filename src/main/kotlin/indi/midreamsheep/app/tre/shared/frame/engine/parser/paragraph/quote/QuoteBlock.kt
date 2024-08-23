package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.quote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockDisplay
import indi.midreamsheep.app.tre.shared.frame.engine.block.context.TREContextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContextComposition
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TREDefaultLinePreButton
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.ui.engine.editor.treEditorWithoutScroll

class QuoteBlock(
    blockManager: TREBlockManager
): TREContextBlock(blockManager.getContext()) {

    constructor(blockManager: TREBlockManager,quoteContext: TREEditorContext):this(blockManager){
        innerContext = quoteContext
    }
    
    private val quoteBlockDisplay = object : TREBlockDisplay {
        override fun getDisplay()=Display {
            {
                Row(Modifier.fillMaxWidth().background(Color(0xFF2D2D2))) {
                    var height by remember { mutableStateOf(20.dp) }
                    Box(Modifier.height(height).width(5.dp).background(Color.Green))
                    Spacer(Modifier.width(3.dp))
                    Box(Modifier.weight(1f).onGloballyPositioned { height = it.size.height.dp } ){
                        CompositionLocalProvider(getEditorContextComposition() provides innerContext){
                            treEditorWithoutScroll()
                        }
                    }
                }
            }
        }

        override fun getPreButton() = TREDefaultLinePreButton()

    }
    override fun getTREBlockDisplay() = quoteBlockDisplay


    override fun getOutputContent(): String {
        TODO("Not yet implemented")
    }

    override fun whenInsert() {}

    override fun whenRemove() {}
}