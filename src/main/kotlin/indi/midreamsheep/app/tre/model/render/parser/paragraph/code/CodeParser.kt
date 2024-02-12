package indi.midreamsheep.app.tre.model.render.parser.paragraph.code

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.api.annotation.render.LineParser
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.parser.ParagraphParser
import indi.midreamsheep.app.tre.model.render.parser.paragraph.code.editor.TRECodeLine

@LineParser
@MapKey("`")
class CodeParser: ParagraphParser {
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
        text: TextFieldValue,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreLine
    ): TRETextRender {
        val render = TRETextRender(line)
        render.styleTextTree = StyleTextCodeRoot(text.text)
        render.previewDisplay = Display {
            //对于代码块进行替换
            val newLine = TRELineState(stateList).apply {
                this.line = TRECodeLine(this,text.text).apply {
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

}