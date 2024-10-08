package indi.midreamsheep.app.tre.shared.frame

import indi.midreamsheep.app.tre.api.annotation.render.line.LineParserMap
import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.TRELineStyleParser
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.DefaultParser
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector

@Comment
class ManagerReadParser {

    @LineParserMap
    private val paragraphParser = HashMap<Char,List<TRELineStyleParser>>()

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
            var parser: TRELineStyleParser? = null
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
            lineNumber += parser!!.analyse(split, lineNumber, manager)
        }
        if (manager.getSize()==0){
            manager.addBlock(TRECoreBlock(manager.getContext()))
        }
    }
}