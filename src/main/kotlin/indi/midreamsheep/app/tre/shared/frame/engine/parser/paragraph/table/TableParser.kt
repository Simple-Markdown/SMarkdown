package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.table

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockDelete
import indi.midreamsheep.app.tre.model.editor.operator.core.TREBlockInsert
import indi.midreamsheep.app.tre.model.editor.operator.core.TREOperatorGroup
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf

@LineParserMap
@MapKey("reg:^\\|.+\\|\$")
class TableParser: TRELineStyleParser {
    override fun buildRender(text: String, block: TRECoreBlock): TRERender {
        val render = TRERender(block)
        // 通过文本获取初始文本
        val split = text.split('|')
        // 删除多余数据
        val originalData = split.subList(1, split.size - 1)
        // 构建builder
        val builder = TableBlockBuilder(originalData)
        val blockIndex = block.getEditorContext().blockManager.getCurrentBlockIndex()
        render.styleText.previewDisplay = {
            TableBuilderDialog(builder.originalData.size){
                    row,column->
                val tableBlock = builder.build(block.getEditorContext(), row, column)
                // 对block进行替换
                val context = block.getEditorContext()
                context.blockManager.executeOperator(
                    TREOperatorGroup().apply {
                        addOperator(TREBlockDelete(blockIndex))
                        addOperator(TREBlockInsert(blockIndex,tableBlock))
                    }
                )
            }
        }
        render.styleText.styleTextTree = TRECoreContentLeaf(text)
        return render
    }

    override fun getWeight(text: String) = 2
}