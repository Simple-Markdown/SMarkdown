package indi.midreamsheep.app.markdown.editor.parser

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.markdown.editor.line.core.CoreMarkdownLine
import indi.midreamsheep.app.markdown.editor.manager.MarkdownStateManager

interface ParagraphParser {
    /**
     * 行格式检查
     * 通过检查才会调用getComposable进行渲染
     * */
    fun formatCheck(text: String):Boolean
    /**
     * 获取渲染函数
     * */
    fun getComposable(text: String, recall: () -> Unit, stateList: MarkdownStateManager, state: CoreMarkdownLine):@Composable ()->Unit
    /**
     * text解析，用于对文本进行初始化解释时调用
     * @return Pair<lineNumber,innerNumber> 两者都是下一次解析的起始位置
     * */
    fun analyse(texts:List<String>,lineNumber:Int,innerNumber:Int):Pair<Int,Int>
}