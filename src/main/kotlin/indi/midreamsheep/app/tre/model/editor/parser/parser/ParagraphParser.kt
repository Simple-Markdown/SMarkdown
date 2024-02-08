package indi.midreamsheep.app.tre.model.editor.parser.parser

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.styletext.TREStyleTextTree

interface ParagraphParser {
    /**
     * 行格式检查
     * 通过检查才会调用getComposable进行渲染
     * */
    fun formatCheck(text: String):Boolean
    /**
     * 获取渲染函数
     * */
    fun getAnnotatedString(text: TextFieldValue, stateList: TREStateManager, state: CoreTRELine,recall: ()->Unit):Pair<TREStyleTextTree,Display>


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
        val markdownLineState = TRELineState(state)
        val line = markdownLineState.line
        if (line is CoreTRELine){
            line.content.value = TextFieldValue(texts[lineNumber])
        }
        state.getMarkdownLineStateList().add(markdownLineState)
        return lineNumber+1
    }
}