package indi.midreamsheep.app.tre.model.parser.paragraph

import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.parser.span.TREInlineParser
import indi.midreamsheep.app.tre.model.render.TRERender
import indi.midreamsheep.app.tre.model.render.style.styletext.root.TRECoreStyleTextRoot
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class DefaultParser: indi.midreamsheep.app.tre.model.parser.LineParser {

    @Injector
    private val spanParser: TREInlineParser? = null

    override fun formatCheck(text: String): Boolean {
        return true
    }

    override fun getAnnotatedString(
        text: String,
        selection:Int,
        stateList: TREStateManager,
        line: TRECoreBlock
    ): TRERender {
        val render = TRERender(line)
        val treCoreStyleTextRoot = TRECoreStyleTextRoot()
        render.styleText.styleTextTree = treCoreStyleTextRoot

        val list = spanParser!!.parse(text, selection,line.isFocus.value, render)
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