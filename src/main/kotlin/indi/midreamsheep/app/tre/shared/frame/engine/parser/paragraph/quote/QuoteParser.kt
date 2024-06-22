package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.quote

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowObserverManager
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockDelete
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockInsert
import indi.midreamsheep.app.tre.model.editor.operator.core.TREOperatorGroup
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.core.block.TREBlockManagerImpl
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.TRECoreLineParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@LineParserMap
@MapKey(">")
class QuoteParser: TRELineStyleParser {

    @Injector
    val parser: TRECoreLineParser? = null


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
            treShortcutEvent = QuoteListenerManager()
        )
        val quoteBlock = QuoteBlock(editorContext.blockManager)
        context.blockManager.executeOperator(
            TREOperatorGroup().apply {
                addOperator(TREBlockDelete(context.blockManager.getCurrentBlockIndex()))
                addOperator(TREBlockInsert(context.blockManager.getCurrentBlockIndex(),quoteBlock))
            }
        )
        editorContext.blockManager.addBlock(TRECoreBlock(editorContext.blockManager))
        editorContext.blockManager.focusBlock(0)
        (editorContext.blockManager.getCurrentBlock()!! as TRECoreBlock).setTextFieldValue(TextFieldValue(text.substring(2)))
        return render
    }

    override fun getWeight(text: String) = 2
}