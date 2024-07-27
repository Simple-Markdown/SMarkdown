package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.quote

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowObserverManager
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockDelete
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockInsert
import indi.midreamsheep.app.tre.model.editor.operator.core.TREOperatorGroup
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.ManagerReadParser
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.blockmanager.TREBlockManagerImpl
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@LineParserMap
@MapKey(">")
class QuoteParser: TRELineStyleParser {

    @Injector
    var parser: ManagerReadParser? = null

    override fun formatCheck(text: String, blockManager: TREBlockManager, lineNumber: Int): Boolean {
        return text.startsWith("> ")
    }

    override fun buildRender(
        text: String,
        block: TRECoreBlock
    ): TRERender {
        val render = TRERender(block).apply {
            styleText.styleTextTree = TRECoreContentLeaf("quote init")
        }
        val context = block.getBlockManager().getContext()
        val editorContext = TREEditorContext(
            parentContext =  context,
            keyManager = context.keyManager,
            blockManager = TREBlockManagerImpl(),
            treObserverManager = TREEditorWindowObserverManager(),
            treShortcutEvent = QuoteListenerManager(),
            metaData = context.metaData,
        )
        val quoteBlock = QuoteBlock(block.getBlockManager(),editorContext)
        editorContext.block = quoteBlock
        context.blockManager.executeOperator(
            TREOperatorGroup().apply {
                addOperator(TREBlockDelete(context.blockManager.getCurrentBlockIndex()))
                addOperator(TREBlockInsert(context.blockManager.getCurrentBlockIndex(),quoteBlock))
            }
        )
        editorContext.blockManager.addBlock(TRECoreBlock(editorContext.blockManager))
        editorContext.blockManager.focusBlock(0,-1)
        (editorContext.blockManager.getTREBlock(0) as TRECoreBlock).setTextFieldValue(TextFieldValue(text.substring(2)))
        return render
    }

    override fun getWeight(text: String) = 2

    override fun analyse(texts: List<String>, lineNumber: Int, treBlockManager: TREBlockManager): Int {
        // 获取接下来的所有的> 开头的行，并拼接
        val stringBuilder = StringBuilder(texts[lineNumber].substring(2))
        var count = 1
        while (lineNumber+count <= texts.size-1&&texts[lineNumber+count].startsWith("> ")) {
            stringBuilder.append("\n"+texts[lineNumber+count].substring(2))
            count++
        }
        // 根据 stringBuilder构建quote block
        val context = treBlockManager.getContext()
        val editorContext = TREEditorContext(
            parentContext =  context,
            keyManager = context.keyManager,
            blockManager = TREBlockManagerImpl(),
            treObserverManager = TREEditorWindowObserverManager(),
            treShortcutEvent = QuoteListenerManager(),
            metaData = context.metaData,
        )
        val quoteBlock = QuoteBlock(treBlockManager,editorContext)
        editorContext.block = quoteBlock
        treBlockManager.addBlock(quoteBlock)
        parser!!.parse(editorContext.blockManager,stringBuilder.toString())
        return count
    }
}