package indi.midreamsheep.app.tre.model.parser.paragraph.code

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserList
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.parser.paragraph.code.editor.TRECodeLine
import indi.midreamsheep.app.tre.model.render.TRERender
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey

@LineParserList
@MapKey("`")
class CodeParser: indi.midreamsheep.app.tre.model.parser.LineParser {
    /**
     * 行格式检查
     * 通过检查才会调用getComposable进行渲染
     * */
    override fun formatCheck(text: String): Boolean {
        return text.startsWith("```")
    }

    /**
     * 获取渲染函数
     * */
    override fun getAnnotatedString(
        text: String,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreLine
    ): TRERender {
        val render = TRERender(line)
        render.styleText.styleTextTree = StyleTextCodeRoot(text)
        render.styleText.preview = false
        render.styleText.previewDisplay = Display {
            //对于代码块进行替换
            val newLine = TRELineState(stateList).apply {
                this.line = TRECodeLine(this,text).apply {
                    focus.value = true
                }
            }
            val list = stateList.getMarkdownLineStateList()
            list.indexOf(line.wrapper).let {
                list[it] = newLine
            }
        }
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
    override fun analyse(texts: List<String>, lineNumber: Int, state: TREStateManager): Int {
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
                val newLine = TRELineState(state).apply {
                    this.line = TRECodeLine(this,type).apply {
                        content.value = TextFieldValue(code.toString())
                    }
                }
                state.getMarkdownLineStateList().add(newLine)
                return index+1
            }
            index++
        }
        return super.analyse(texts, lineNumber, state)
    }
}