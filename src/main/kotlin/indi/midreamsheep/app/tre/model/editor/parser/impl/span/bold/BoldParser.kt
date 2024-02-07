package indi.midreamsheep.app.tre.model.editor.parser.impl.span.bold

import indi.midreamsheep.app.tre.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.tre.model.editor.parser.SpanParse
import indi.midreamsheep.app.tre.model.editor.parser.parser.SpanParser
import indi.midreamsheep.app.tre.model.styletext.StyleTextTree
import indi.midreamsheep.app.tre.model.styletext.pojo.StyleTextOffsetMapping
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector
import lombok.extern.slf4j.Slf4j

@MapInjector(target = "span",key="*")
@Comment
@Slf4j
class BoldParser: SpanParser {

    @Injector
    private val spanParse: SpanParse? = null

    override fun formatCheck(text: String): Boolean {
        if (text.length<4) return false
        if (text[0]!='*'||text[1]!='*') return false
        var pointer = 2
        while (pointer<text.length-1) {
            if (text[pointer]=='*'&&text[pointer+1]=='*') return true
            pointer++
        }
        return false
    }

    override fun getWeight(text: String): Int = 4


    override fun generateLeaf(
        text: String,
        selection: Int,
        isFocus: Boolean,
        styleTextOffsetMapping: StyleTextOffsetMapping
    ): StyleTextTree {

        var pointer = 2
        while (pointer<text.length-1) {
            if (text[pointer]=='*'&&text[pointer+1]=='*') break
            pointer++
        }
        //找到下一个不为*的位置
        pointer++
        while (pointer<text.length) {
            if (text[pointer]!='*') break
            pointer++
        }
        val isDisplay = selection in 2..pointer

        val substring:String = text.substring(2, pointer - 2)

        val boldLeaf = StyleTextBoldLeaf(
            substring,
            StyleTextOffsetMapping(
                styleTextOffsetMapping.originalOffsetStart,
                styleTextOffsetMapping.transformedOffsetStart
            ),
            isDisplay
        )

        val originalOffsetStart = styleTextOffsetMapping.originalOffsetStart + 2
        val transformedOffsetStart = styleTextOffsetMapping.transformedOffsetStart+if (isDisplay) 2 else 0

        val list = spanParse!!.parse(substring,selection-2,isFocus,
            StyleTextOffsetMapping(originalOffsetStart, transformedOffsetStart)
        )

        list.forEach {
            boldLeaf.addChildren(it)
        }
        return boldLeaf
    }
}