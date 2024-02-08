package indi.midreamsheep.app.tre.model.editor.parser.impl.paragraph

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.editor.parser.SpanParse
import indi.midreamsheep.app.tre.model.editor.parser.parser.ParagraphParser
import indi.midreamsheep.app.tre.model.styletext.TREStyleTextTree
import indi.midreamsheep.app.tre.model.styletext.pojo.TREStyleTextOffsetMapping
import indi.midreamsheep.app.tre.model.styletext.root.TRECoreStyleTextRoot
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class DefaultParser: ParagraphParser {

    @Injector
    private val spanParser: SpanParse? = null

    override fun formatCheck(text: String): Boolean {
        return true
    }

    override fun getAnnotatedString(
        text: TextFieldValue,
        stateList: TREStateManager,
        state: CoreTRELine
    ): TREStyleTextTree {
        val treCoreStyleTextRoot = TRECoreStyleTextRoot()
        val list = spanParser!!.parse(text.text, text.selection.start,state.isFocus.value,
            TREStyleTextOffsetMapping(0,0) )
        for (styleTextLeaf in list) {
            treCoreStyleTextRoot.addChildren(styleTextLeaf)
        }
        return treCoreStyleTextRoot
    }

    /**
     * 当多个解析器都能解析时，通过权重来判断
     * 权重为语法的复杂度，越复杂的语法权重越高，一般为特征字符的数量
     * */
    override fun getWeight(text: String): Int {
        return 0
    }

}