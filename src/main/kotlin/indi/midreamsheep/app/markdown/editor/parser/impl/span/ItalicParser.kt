package indi.midreamsheep.app.markdown.editor.parser.impl.span

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import indi.midreamsheep.app.markdown.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.markdown.editor.parser.SpanParse
import indi.midreamsheep.app.markdown.editor.parser.SpanParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@MapInjector(target = "span",key="*")
@Comment
class ItalicParser: SpanParser {

    @Injector
    private val spanParse:SpanParse? = null

    @Injector
    private val boldParser:BoldParser? = null

    override fun formatCheck(text: String): Boolean {
        if (boldParser!!.formatCheck(text)) {
            return false
        }
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

    override fun generateAnnotatedString(text: String): Triple<Int, AnnotatedString, List<@Composable () -> Unit>> {
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
        val (annotatedString, function0List) = spanParse!!.parse(substring)
        val resultAnnotatedString = buildAnnotatedString {
            withStyle(
                style = androidx.compose.ui.text.SpanStyle(
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            ) {
                append(annotatedString)
            }
        }
        return Triple(pointer,resultAnnotatedString,function0List)
    }
}