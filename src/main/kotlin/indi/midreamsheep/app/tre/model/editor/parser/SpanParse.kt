package indi.midreamsheep.app.tre.model.editor.parser

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import indi.midreamsheep.app.tre.context.di.inject.mapdi.annotation.MapInjector
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@Comment
class SpanParse {
    @MapInjector(target = "span")
    private val spanParserList = HashMap<Char, List<SpanParser>>()

    fun parse(
        text:String
    ): Pair<AnnotatedString, List<@Composable ()->Unit>>
    {
        var pointer = 0
        var resultAnnotatedString = AnnotatedString("")
        val resultList = ArrayList<@Composable ()->Unit>()

        var normalString = ""

        while(true) {
            if (pointer>=text.length) break
            val startChar = text[pointer]
            val spanParsers = spanParserList[startChar]

            if (spanParsers==null) {
                normalString += text[pointer]
                pointer++
                continue
            }
            if (normalString.isNotEmpty()) {
                resultAnnotatedString += AnnotatedString(normalString)
                normalString = ""
            }
            var flag = false
            spanParsers.forEach {
                if (it.formatCheck(text.substring(pointer))){
                    val (chatNumber, annotatedString, list) = it.generateAnnotatedString(text.substring(pointer))
                    pointer+=chatNumber
                    resultAnnotatedString += annotatedString
                    resultList.addAll(list)
                    flag = true
                }
            }
            if (!flag) {
                pointer++
                normalString += startChar
            }
        }
        if (normalString.isNotEmpty()) {
            resultAnnotatedString += AnnotatedString(normalString)
        }
        return Pair(resultAnnotatedString,resultList)
    }
}