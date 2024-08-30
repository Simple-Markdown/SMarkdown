package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list.unordered

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowObserverManager
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockDelete
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockInsert
import indi.midreamsheep.app.tre.model.editor.operator.core.TREOperatorGroup
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list.ListBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list.ListShortcutEvent
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list.ListType
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.manager.blockmanager.TREBlockManagerImpl

@LineParserMap
@MapKey("reg:- .*")
class UnorderedListParser: TRELineStyleParser {

    override fun buildRender(
        text: String,
        block: TRECoreBlock
    ): TRERender {
        val render = TRERender().apply {
            styleText.styleTextTree = TRECoreContentLeaf("quote init")
        }
        val context = block.getBlockManager().getContext()
        val editorContext = TREEditorContext(
            parentContext =  context,
            keyManager = context.keyManager,
            blockManager = TREBlockManagerImpl(),
            treObserverManager = TREEditorWindowObserverManager(),
            treShortcutEvent = ListShortcutEvent(),
            metaData = context.metaData,
        )

        val listBlock = ListBlock(block.getBlockManager(),UnorderedListType(),editorContext)
        editorContext.block = listBlock
        context.blockManager.executeOperator(
            TREOperatorGroup().apply {
                addOperator(TREBlockDelete(context.blockManager.getCurrentBlockIndex()))
                addOperator(TREBlockInsert(context.blockManager.getCurrentBlockIndex(),listBlock))
            }
        )
        editorContext.blockManager.addBlock(TRECoreBlock(editorContext.blockManager))
        editorContext.blockManager.focusBlock(0)
        (editorContext.blockManager.getTREBlock(0) as TRECoreBlock).setTextFieldValue(TextFieldValue(text.substring(2)))
        return render
    }
    override fun getWeight(text: String): Int {
        return 1
    }


}

class UnorderedListType:ListType{
    override fun getPrefix() = Display {
        {
            Box(
                Modifier
                    .size(25.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(6.dp)
                        .clip(RoundedCornerShape(100))
                        .background(Color.Black)
                ) {
                }
            }
        }
    }

    /**
     * 获取前缀输出节点，用于输出文本时使用
     * */
    override fun getPrefixText() = "- "

    /**
     * 根据文本构建新的list
     * */
    override fun build(text: String, context: TREEditorContext): ListBlock {
        val editorContext = TREEditorContext(
            parentContext =  context,
            keyManager = context.keyManager,
            blockManager = TREBlockManagerImpl(),
            treObserverManager = TREEditorWindowObserverManager(),
            treShortcutEvent = ListShortcutEvent(),
            metaData = context.metaData,
        )

        val listBlock = ListBlock(context.blockManager,UnorderedListType(),editorContext)
        editorContext.block = listBlock
        editorContext.blockManager.addBlock(TRECoreBlock(editorContext.blockManager))
        (editorContext.blockManager.getTREBlock(0) as TRECoreBlock).setTextFieldValue(TextFieldValue(text))
        return listBlock
    }


}