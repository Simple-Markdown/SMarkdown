package indi.midreamsheep.app.tre.model.editor.parser.parser

import indi.midreamsheep.app.tre.model.styletext.TREStyleTextTree
import indi.midreamsheep.app.tre.model.styletext.pojo.TREStyleTextOffsetMapping

interface SpanParser {
    fun formatCheck(text: String): Boolean
    fun getWeight(text: String): Int
    fun generateLeaf(
        text: String,
        selection: Int,
        isFocus: Boolean,
        styleTextOffsetMapping: TREStyleTextOffsetMapping
    ): TREStyleTextTree
}