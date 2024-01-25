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
class BoldParser: SpanParser {

    @Injector
    private val spanParse:SpanParse? = null

    override fun formatCheck(text: String): Boolean {
        if (text.length<4) return false
        //不是两个以上的*
        if (text[0]!='*'||text[1]!='*') return false
        //存在两个连续的*
        var pointer = 2
        while (pointer<text.length-1) {
            if (text[pointer]=='*'&&text[pointer+1]=='*') return true
            pointer++
        }
        //不存在两个连续的*
        return false
    }

    /**
     *
     * */
    override fun generateAnnotatedString(text: String): Triple<Int, AnnotatedString, List<@Composable () -> Unit>> {
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
        val substring:String = text.substring(2, pointer - 2)
        val (annotatedString, function0List) = spanParse!!.parse(substring)
        val resultAnnotatedString = buildAnnotatedString {
            withStyle(
                style = androidx.compose.ui.text.SpanStyle(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
            ) {
                append(annotatedString)
            }
        }
        return Triple(pointer,resultAnnotatedString,function0List)
    }
}