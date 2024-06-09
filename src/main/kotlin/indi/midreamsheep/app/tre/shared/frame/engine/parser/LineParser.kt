package indi.midreamsheep.app.tre.shared.frame.engine.parser

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TREBlockState
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender

interface LineParser {
    /**
     * 行格式检查
     * 通过检查才会调用getComposable进行渲染
     * */
    fun formatCheck(
        text: String,
        blockManager: TREBlockManager,
        lineNumber: Int
    ):Boolean = true
    /**
     * 获取渲染函数
     * */
    fun buildRender(text: String, block: TRECoreBlock): TRERender


    /**
     * 当多个解析器都能解析时，通过权重来判断
     * 权重为语法的复杂度，越复杂的语法权重越高，一般为特征字符的数量
     * */
    fun getWeight(text: String):Int
    /**
     * text解析，用于对文本进行初始化解释时调用
     * @return下一次解析的起始位置
     * */
    fun analyse(texts:List<String>, lineNumber:Int, state: TREBlockManager):Int{
        val markdownLineState = TREBlockState(state)
        val line = markdownLineState.block
        if (line is TRECoreBlock){
            line.content.value = TextFieldValue(texts[lineNumber])
        }
        state.getTREBlockStateList().add(markdownLineState)
        return lineNumber+1
    }
}