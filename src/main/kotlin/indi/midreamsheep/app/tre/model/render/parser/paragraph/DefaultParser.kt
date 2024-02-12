package indi.midreamsheep.app.tre.model.render.parser.paragraph

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.TREInlineParser
import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.parser.ParagraphParser
import indi.midreamsheep.app.tre.model.render.styletext.root.TRECoreStyleTextRoot
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class DefaultParser: ParagraphParser {

    @Injector
    private val spanParser: TREInlineParser? = null

    override fun formatCheck(text: String): Boolean {
        return true
    }

    override fun getAnnotatedString(
        text: TextFieldValue,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreLine
    ): TRETextRender {
        val render = TRETextRender(line)
        val treCoreStyleTextRoot = TRECoreStyleTextRoot()
        render.styleTextTree = treCoreStyleTextRoot

        val list = spanParser!!.parse(text.text, selection,line.isFocus.value, render)
        for (styleTextLeaf in list) {
            treCoreStyleTextRoot.addChildren(styleTextLeaf)
        }
        return render
    }

    /**
     * 当多个解析器都能解析时，通过权重来判断
     * 权重为语法的复杂度，越复杂的语法权重越高，一般为特征字符的数量
     * */
    override fun getWeight(text: String): Int {
        return 0
    }

}