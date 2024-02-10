package indi.midreamsheep.app.tre.model.render.styletext

/**
 * 文本偏移映射，用于当前叶节点的起始偏移
 * */
data class TREStyleTextOffsetMapping(
    /**
     * 在原始文本中的起始偏移
     * 例如：原始文本为"Hello World"，当前叶节点的内容为"Hello"，则originalOffsetStart为0
     *                                                   当前叶节点的内容为"World"，则originalOffsetStart为6
     * */
    val originalOffsetStart: Int,
    /**
     * 在转换后文本中的起始偏移
     * 例如：转换后文本为"**hello** word"(*隐藏时)，当前叶节点的内容为"hello"，则transformedOffsetStart为0
     *                                                            当前叶节点的内容为"word"，则transformedOffsetStart为6
     * */
    val transformedOffsetStart: Int,
)