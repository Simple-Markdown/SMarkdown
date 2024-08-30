package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.latex

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockDelete
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockInsert
import indi.midreamsheep.app.tre.model.editor.operator.core.TREOperatorGroup
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager

@LineParserMap
@MapKey("start:$")
class LatexParser: TRELineStyleParser {

    override fun buildRender(
        text: String,
        block: TRECoreBlock
    ): TRERender {
        val render = TRERender()
        render.styleText.styleTextTree = TRECoreContentLeaf(text)
        val currentBlockIndex = block.getEditorContext().blockManager.getCurrentBlockIndex()
        render.styleText.previewDisplay = {
            block.getEditorContext().blockManager.executeOperator(
                TREOperatorGroup().apply {
                    addOperator(TREBlockDelete(currentBlockIndex))
                    addOperator(TREBlockInsert(currentBlockIndex,LatexBlock(block.getEditorContext())))
                }
            )
        }
        return render
    }

    override fun getWeight(text: String): Int {
        return 2
    }

    override fun formatCheck(text: String, blockManager: TREBlockManager, lineNumber: Int): Boolean {
        return text == ("$$")
    }
}