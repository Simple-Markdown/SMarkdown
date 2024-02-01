package indi.midreamsheep.app.markdown.model.editor.parser

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.markdown.model.editor.line.TRELineState
import indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager

interface ParagraphParser {
    /**
     * 行格式检查
     * 通过检查才会调用getComposable进行渲染
     * */
    fun formatCheck(text: String):Boolean
    /**
     * 获取渲染函数
     * */
    fun getComposable(text: String, recall: () -> Unit, stateList: indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager, state: indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine):@Composable ()->Unit
    /**
     * text解析，用于对文本进行初始化解释时调用
     * @return下一次解析的起始位置
     * */
    fun analyse(texts:List<String>, lineNumber:Int, state: indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager):Int{
        val markdownLineState = indi.midreamsheep.app.markdown.model.editor.line.TRELineState(state)
        val line = markdownLineState.line
        if (line is indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine){
            line.content.value = texts[lineNumber]
        }
        state.getMarkdownLineStateList().add(markdownLineState)
        return lineNumber+1
    }
}