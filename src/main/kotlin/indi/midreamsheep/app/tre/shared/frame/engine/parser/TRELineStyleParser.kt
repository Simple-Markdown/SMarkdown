package indi.midreamsheep.app.tre.shared.frame.engine.parser

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender

interface TRELineStyleParser {
    /**
     * 行格式检查
     * 通过检查才会调用getComposable进行渲染
     * */
    fun formatCheck(text: String, blockManager: TREBlockManager, lineNumber: Int):Boolean = true
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
     * @return 单次解析所处理的行数
     * */
    fun analyse(texts:List<String>, lineNumber:Int, treBlockManager: TREBlockManager):Int{
        val markdownLineState = TRECoreBlock(treBlockManager)
        markdownLineState.content.value = TextFieldValue(texts[lineNumber])
        treBlockManager.addBlock(markdownLineState)
        return 1
    }
}