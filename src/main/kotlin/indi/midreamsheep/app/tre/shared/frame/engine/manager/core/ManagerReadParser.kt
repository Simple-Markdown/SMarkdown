package indi.midreamsheep.app.tre.shared.frame.engine.manager.core

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TREBlockState
import indi.midreamsheep.app.tre.shared.frame.engine.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.parser.LineParser
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.DefaultParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class ManagerReadParser {

    @LineParserMap
    private val paragraphParser = HashMap<Char,List<LineParser>>()

    @Injector
    private val defaultParser: DefaultParser? = null

    fun parse(manager: TREBlockManager, content: String) {
        var lineNumber = 0
        val split = content.split("\n")
        if (split.isEmpty()) return
        while (true){
            if (lineNumber==split.size){
                break
            }
            var parser: LineParser? = null
            if(split[lineNumber].isNotEmpty()){
                //获取首字母
                val start = split[lineNumber][0]
                //获取解析器
                val paragraphParsers = paragraphParser[start]
                paragraphParsers?.forEach {
                    if (it.formatCheck(split[lineNumber],manager,lineNumber)){
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
        if (manager.getTREBlockStateList().size==0){
            manager.getTREBlockStateList().add(TREBlockState(manager))
        }
    }
}