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
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContextComposition
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TREDefaultLinePreButton
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.ui.engine.editor.treEditorWithoutScroll

class ListBlock(
    blockManager: TREBlockManager
): TREContextBlock(blockManager.getContext()) {

    lateinit var listType: ListType
    constructor(blockManager: TREBlockManager,listType: ListType,listContext: TREEditorContext):this(blockManager){
        this.listType = listType
        innerContext = listContext
    }
    
    private val quoteBlockDisplay = object : TREBlockDisplay {
        override fun getDisplay()= Display {
            {
                Row(Modifier.fillMaxWidth()) {
                    listType.getPrefix().getComposable().invoke()
                    Box(Modifier.weight(1f)){
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