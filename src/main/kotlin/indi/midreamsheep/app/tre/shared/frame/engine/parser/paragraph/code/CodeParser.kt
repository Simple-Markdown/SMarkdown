package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.code

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.code.editor.TRECodeBlock
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender

@LineParserMap
@MapKey("`")
class CodeParser: TRELineStyleParser {
    /**
     * 行格式检查
     * 通过检查才会调用getComposable进行渲染
     * */
    override fun formatCheck(text: String, blockManager: TREBlockManager, lineNumber: Int): Boolean {
        return text.startsWith("```")
    }

    /**
     * 获取渲染函数
     * */
    override fun buildRender(
        text: String,
        block: TRECoreBlock
    ): TRERender {
        val render = TRERender(block)
        //TODO rebuild the code parser
        return render
    }

    /**
     * 当多个解析器都能解析时，通过权重来判断
     * 权重为语法的复杂度，越复杂的语法权重越高，一般为特征字符的数量
     * */
    override fun getWeight(text: String): Int {
        return 3
    }

    /**
     * text解析，用于对文本进行初始化解释时调用
     * @return下一次解析的起始位置
     * */
    override fun analyse(texts: List<String>, lineNumber: Int, treBlockManager: TREBlockManager): Int {
        //找到下一个结束
        var index = lineNumber+1
        while (true){
            if (index==texts.size){
                break
            }
            if (texts[index].startsWith("```")){
                //获取代码块类型
                val type = texts[lineNumber].replace("```","")
                //组合代码块
                val code = StringBuilder()
                for (i in lineNumber+1 until index){
                    code.append(texts[i]).append("\n")
                }
                if (index-lineNumber-1>0){
                    code.delete(code.length-1,code.length)
                }
                val newLine = TRECodeBlock(treBlockManager,type).apply {
                        content.value = TextFieldValue(code.toString())
                }
                treBlockManager.addBlock(newLine)
                return index+1
            }
            index++
        }
        return super.analyse(texts, lineNumber, treBlockManager)
    }
}