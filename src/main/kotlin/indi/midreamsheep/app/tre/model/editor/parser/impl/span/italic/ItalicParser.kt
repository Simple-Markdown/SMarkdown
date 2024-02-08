package indi.midreamsheep.app.tre.model.editor.parser.impl.span.italic

import indi.midreamsheep.app.tre.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.tre.model.editor.parser.SpanParse
import indi.midreamsheep.app.tre.model.editor.parser.parser.SpanParser
import indi.midreamsheep.app.tre.model.styletext.TREStyleTextTree
import indi.midreamsheep.app.tre.model.styletext.pojo.TREStyleTextOffsetMapping
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@MapInjector(target = "span",key="*")
@Comment
class ItalicParser: SpanParser {

    @Injector
    private val spanParse: SpanParse? = null

    override fun formatCheck(text: String): Boolean {
        if (text.length<2) return false
        //找到下一个不为*的位置
        var pointer = 0
        while (pointer<text.length) {
            if (text[pointer]!='*') break
            pointer++
        }
        if (pointer==text.length) return false
        //找到下一个为*的位置
        while (pointer<text.length) {
            if (text[pointer]=='*') return true
            pointer++
        }
        return false
    }

    override fun getWeight(text: String): Int {
        return 2
    }

    override fun generateLeaf(
        text: String,
        selection: Int,
        isFocus: Boolean,
        styleTextOffsetMapping: TREStyleTextOffsetMapping
    ): TREStyleTextTree {
        var pointer = 1
        while (pointer<text.length-1) {
            if (text[pointer]=='*') break
            pointer++
        }
        //找到下一个不为*的位置
        pointer++
        while (pointer<text.length) {
            if (text[pointer]!='*') break
            pointer++
        }
        val substring:String = text.substring(1, pointer - 1)

        val isDisplay = selection in 0..pointer&&isFocus

        val originalOffsetStart = styleTextOffsetMapping.originalOffsetStart + 1
        val transformedOffsetStart = styleTextOffsetMapping.transformedOffsetStart + if (isDisplay) 1 else 0

        val (childrenList) = spanParse!!.parse(substring,selection-1,isFocus,TREStyleTextOffsetMapping(
            originalOffsetStart, transformedOffsetStart
        ))
        val italicLeaf = StyleTextItalicLeaf(
            substring,
            TREStyleTextOffsetMapping(
                styleTextOffsetMapping.originalOffsetStart,
                styleTextOffsetMapping.transformedOffsetStart
            ),
            isDisplay
        )
        italicLeaf.addChildren(childrenList)
        return italicLeaf
    }
}