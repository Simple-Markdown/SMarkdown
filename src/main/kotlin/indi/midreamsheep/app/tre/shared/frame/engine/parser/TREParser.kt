package indi.midreamsheep.app.tre.shared.frame.engine.parser

import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.TRELineParserManager
import indi.midreamsheep.app.tre.shared.frame.engine.parser.span.TREInlineParserManager
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TREStyleTextTreeInter
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot

fun treLineParse(
    text: String,
    block: TRECoreBlock,
): TRERender {
    if(text.isEmpty()) {
        val treCoreStyleTextRoot = TRECoreTreeRoot().apply {
            addChild(TRECoreContentLeaf(""))
        }
        val render = TRERender(block)
        render.styleText.styleTextTree = treCoreStyleTextRoot
        return render
    }
    return getBean(TRELineParserManager::class.java)
        .get(
            text,block.getBlockManager(),
            block.getBlockManager().indexOf(block)
        )
        .buildRender(text,block)
}


fun treInlineParse(text: String, render: TRERender): List<TREStyleTextTreeInter> {
    // 如果文本为空的话返回默认节点
    if (text.isEmpty()) return listOf(TRECoreContentLeaf(""))
    // 获取起始符解析器
    val spanParserMap = getBean(TREInlineParserManager::class.java).getSpanParserMap()
    // 引用
    var pointer = 0
    // 当前默认样式的文本数据
    var normalString = ""
    // 结果样式树结构
    val resultList = mutableListOf<TREStyleTextTreeInter>()

    while(true) {
        // 如果当前的指针超过了文本长度就停止循环
        if (pointer>=text.length) break
        // 通过起始符获取目标的解析器
        val parserList = mutableListOf<TREInlineStyleParser>()
            .apply {
                spanParserMap[text[pointer]]?.let { treInlineStyleParsers ->
                    treInlineStyleParsers.forEach {
                        if (it.formatCheck(text.substring(pointer))){
                            add(it)
                        }
                    }
                }
            }
        // 进行正则表达式获取解析器
        getBean(TREInlineParserManager::class.java).getRegParserMap().forEach{ (reg, parser) ->
            val find = reg.toRegex().find(text, pointer)
            if(find != null) {
                val first = find.range.first
                if(first == pointer){
                    parserList.add(parser)
                }
            }
        }

        if (parserList.isEmpty()){
            normalString += text[pointer]
            pointer++
            continue
        }
        var weight = 0
        var parser: TREInlineStyleParser? = null
        parserList.forEach {
            val w = it.getWeight(text.substring(pointer))
            if (w>weight){
                weight = w
                parser = it
            }
        }
        if (parser==null){
            normalString += text[pointer]
            pointer++
            continue
        }
        // 处理普通文本
        if (normalString.isNotEmpty()) {
            resultList.add(TRECoreContentLeaf(normalString))
            normalString = ""
        }
        val leaf = parser!!.generateLeaf(text.substring(pointer), render)
        resultList.add(leaf)
        pointer += leaf.originalSize()
    }
    if (normalString.isNotEmpty()) {
        resultList.add(TRECoreContentLeaf(normalString))
    }
    return resultList
}