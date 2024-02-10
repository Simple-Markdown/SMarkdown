package indi.midreamsheep.app.tre.model.editor.manager.core

import indi.midreamsheep.app.tre.context.di.inject.mapdi.annotation.MapInject
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.parser.ParagraphParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector
import indi.midreamsheep.app.tre.model.render.parser.paragraph.DefaultParser

@Comment
class ManagerReadParser {

    @MapInject(value = "paragraph")
    private val paragraphParser = HashMap<Char,List<ParagraphParser>>()

    @Injector
    private val defaultParser: DefaultParser? = null

    fun parse(manager: TREStateManager, content: String) {
        var lineNumber = 0
        val split = content.split("\n")
        if (split.isEmpty()) return
        while (true){
            if (lineNumber==split.size){
                break
            }
            var parser: ParagraphParser? = null
            if(split[lineNumber].isNotEmpty()){
                //获取首字母
                val start = split[lineNumber][0]
                //获取解析器
                val paragraphParsers = paragraphParser[start]
                paragraphParsers?.forEach {
                    if (it.formatCheck(split[lineNumber])){
                        parser = it
                    }
                }
            }
            if (parser==null){
                parser = defaultParser
            }
            //解析
            lineNumber = parser!!.analyse(split, lineNumber, manager)
        }
        if (manager.getMarkdownLineStateList().size==0){
            manager.getMarkdownLineStateList().add(TRELineState(manager))
        }
    }
}