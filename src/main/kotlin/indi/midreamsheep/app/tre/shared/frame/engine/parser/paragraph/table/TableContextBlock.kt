package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockComposeState
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockDisplay
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.block.context.TREContextBlock

class TableContextBlock(
    context:TREEditorContext
): TREContextBlock(context) {

    private lateinit var tableBlockContext:TREEditorContext
    private var rowSize = 0
    private var columnSize = 0
    // 用于管理表格的宽，宽有第一行表格头决定
    private var widths: SnapshotStateList<Int> = mutableStateListOf()

    constructor(context: TREEditorContext,
                tableBlockContext: TREEditorContext,
                originalData:List<String>,
                rowSize:Int,
                columnSize:Int
    ):this(context){
        this.tableBlockContext = tableBlockContext
        // 插入初始化数据
        for(i in 0..<rowSize){
            if (i<originalData.size){
                tableBlockContext.blockManager.addBlock(
                    TableItemBlock(
                        context = tableBlockContext,
                        isHeader = true,
                        initContent = originalData[i]
                    )
                )
            } else{
                tableBlockContext.blockManager.addBlock(
                    TableItemBlock(
                        context = tableBlockContext,
                        isHeader = true,
                        initContent = ""
                    )
                )
            }
        }
        // 初始化空blockManager
        repeat(rowSize * (columnSize - 1)){
            tableBlockContext.blockManager.addBlock(
                TableItemBlock(
                    context = tableBlockContext,
                    isHeader = false,
                    initContent = ""
                )
            )
        }
        this.rowSize = rowSize
        this.columnSize = columnSize
    }

    // 构建用于显示的实体类
    private val blockDisplay = object : TREBlockDisplay{
        override fun getDisplay() = Display {
            {
                // 构建表格
                Column{
                    // 构建表头
                    Row {
                        for(i in 0..<rowSize){
                            Box(
                                Modifier.background(Color.Gray.copy(alpha = 0.05f))
                                    .border(1.dp,Color.Black)
                            ){
                                (tableBlockContext.blockManager.getTREBlock(i) as TableItemBlock)
                                    .getComposable(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(tableBlockContext.metaData.editorWith.value/rowSize)

                                    ).invoke()
                            }
                        }
                    }
                    // 构建表格数据
                    for(i in 1..<columnSize){
                        Row(Modifier.height(IntrinsicSize.Max)) {
                            for(j in 0..<rowSize){
                                Box(
                                    Modifier.border(1.dp,Color.Black)
                                ){
                                    (tableBlockContext.blockManager.getTREBlock(i*rowSize+j) as TableItemBlock)
                                        .getComposable(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .width(tableBlockContext.metaData.editorWith.value/rowSize)
                                        ).invoke()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun focusInStart() {
        tableBlockContext.blockManager.focusBlock(0)
    }

    override fun focusInEnd() {
        TODO("Not yet implemented")
    }

    override fun focusStandard() {
        tableBlockContext.blockManager.focusBlock(0)
    }

    override fun inTargetPositionDown(xPositionData: XPositionData) {
        TODO("Not yet implemented")
    }

    override fun inTargetPositionUp(xPositionData: XPositionData) {
        TODO("Not yet implemented")
    }

    override fun releaseFocus() {
        tableBlockContext.blockManager.getCurrentBlock()?.releaseFocus()
        tableBlockContext.blockManager.setCurrentBlock(null)
    }

    override fun getComposeState(): TREBlockComposeState {
        TODO("Not yet implemented")
    }

    override fun getTREBlockDisplay() = blockDisplay

    override fun getOutputContent(): String {
        TODO("Not yet implemented")
    }

    override fun whenInsert() {}

    override fun whenRemove() {}
}

