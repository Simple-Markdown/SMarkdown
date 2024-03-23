package indi.midreamsheep.app.tre.model.parser

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.model.editor.block.TREBlockState
import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.TRERender

interface LineParser {
    /**
     * 行格式检查
     * 通过检查才会调用getComposable进行渲染
     * */
    fun formatCheck(text: String):Boolean = true
    /**
     * 获取渲染函数
     * */
    fun getAnnotatedString(
        text: String,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreBlock
    ):TRERender


    /**
     * 当多个解析器都能解析时，通过权重来判断
     * 权重为语法的复杂度，越复杂的语法权重越高，一般为特征字符的数量
     * */
    fun getWeight(text: String):Int
    /**
     * text解析，用于对文本进行初始化解释时调用
     * @return下一次解析的起始位置
     * */
    fun analyse(texts:List<String>, lineNumber:Int, state: TREStateManager):Int{
        val markdownLineState = TREBlockState(state)
        val line = markdownLineState.line
        if (line is TRECoreBlock){
            line.content.value = TextFieldValue(texts[lineNumber])
        }
        state.getTREBlockStateList().add(markdownLineState)
        return lineNumber+1
    }
}