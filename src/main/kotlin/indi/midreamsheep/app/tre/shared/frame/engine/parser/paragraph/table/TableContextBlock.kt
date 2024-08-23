package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockDisplay
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREFocusEnum
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.block.context.TREContextBlock

class TableContextBlock(
    context:TREEditorContext
): TREContextBlock(context) {
    
    var rowSize = 0
    var columnSize = 0

    constructor(context: TREEditorContext,
                innerContext: TREEditorContext,
                originalData:List<String>,
                rowSize:Int,
                columnSize:Int
    ):this(context){
        this.innerContext = innerContext
        // 插入初始化数据
        for(i in 0..<rowSize){
            if (i<originalData.size){
                innerContext.blockManager.addBlock(
                    TableItemBlock(
                        context = innerContext,
                        isHeader = true,
                        initContent = originalData[i]
                    )
                )
            } else{
                innerContext.blockManager.addBlock(
                    TableItemBlock(
                        context = innerContext,
                        isHeader = true,
                        initContent = ""
                    )
                )
            }
        }
        // 初始化空blockManager
        repeat(rowSize * (columnSize - 1)){
            innerContext.blockManager.addBlock(
                TableItemBlock(
                    context = innerContext,
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
                                (innerContext.blockManager.getTREBlock(i) as TableItemBlock)
                                    .getComposable(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(innerContext.metaData.editorWith.value/rowSize)

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
                                    (innerContext.blockManager.getTREBlock(i*rowSize+j) as TableItemBlock)
                                        .getComposable(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .width(innerContext.metaData.editorWith.value/rowSize)
                                        ).invoke()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getBlockXPosition(index:Int) = innerContext.blockManager.getTREBlock(index).getComposeState().getPointerAbsoluteXPosition()

    override fun inTargetPositionDown(xPositionData: XPositionData) {
        //TODO further fix
        val blockManager = innerContext.blockManager
        for(i in 0..<rowSize){
            if (getBlockXPosition(i)<=xPositionData.x &&(rowSize>i+1 &&getBlockXPosition(i+1)>=xPositionData.x)){
                blockManager.focusBlock(i,TREFocusEnum.IN_TARGET_POSITION_DOWN,xPositionData)
                return
            }
        }
    }

    override fun inTargetPositionUp(xPositionData: XPositionData) {
        //TODO further fix
        val blockManager = innerContext.blockManager
        for(i in rowSize*(columnSize - 1)..<rowSize*columnSize){
            if (getBlockXPosition(i)<=xPositionData.x &&(rowSize*columnSize>i+1 &&getBlockXPosition(i+1)>=xPositionData.x)){
                blockManager.focusBlock(i,TREFocusEnum.IN_TARGET_POSITION_UP,xPositionData)
            }
        }
    }


    override fun getTREBlockDisplay() = blockDisplay

    override fun getOutputContent(): String {
        TODO("Not yet implemented")
    }

    override fun whenInsert() {}

    override fun whenRemove() {}

    /**
     * 获取指定块的向上的一个块下标，默认为当前块
     * */
    public fun getUpBlock(
        index:Int = innerContext.blockManager.getCurrentBlockIndex()
    ):Int{
        if (index>rowSize-1){
            return index - rowSize
        }
        return -1
    }

    /**
     * 获取指定块的向下的一个块下标，默认为当前块
     * */
    public fun getDownBlock(
        index:Int = innerContext.blockManager.getCurrentBlockIndex()
    ):Int{
        if (index<=rowSize*(columnSize-1)-1){
            return index + rowSize
        }
        return -1
    }
}

