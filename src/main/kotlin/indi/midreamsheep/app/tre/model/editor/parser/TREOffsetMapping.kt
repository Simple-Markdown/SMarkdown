package indi.midreamsheep.app.tre.model.editor.parser

import androidx.compose.ui.text.input.OffsetMapping
import indi.midreamsheep.app.tre.model.styletext.TREStyleTextTree

/**
 * 偏移量映射
 * modifiedCharactersCount: 修改的字符数量
 * 正为增加，负为减少
 * allCount:原文本 总字符数量
 * */
class TREOffsetMapping(private val styleTextTree: TREStyleTextTree) : OffsetMapping {

    override fun originalToTransformed(offset: Int) = styleTextTree.originalToTransformed(offset)

    override fun transformedToOriginal(offset: Int) = styleTextTree.transformedToOriginal(offset)

}