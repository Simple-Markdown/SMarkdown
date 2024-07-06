package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.list

import indi.midreamsheep.app.tre.shared.api.display.Display

interface ListType {
    /**
     * 获取前缀显示节点，用于显示前面的标识
     * */
    fun getPrefix():Display
    /**
     * 获取前缀输出节点，用于输出文本时使用
     * */
    fun getPrefixText():String
}