package indi.midreamsheep.app.tre.model.editor.parser.parser

import indi.midreamsheep.app.tre.model.styletext.StyleTextTree
import indi.midreamsheep.app.tre.model.styletext.pojo.StyleTextOffsetMapping

interface SpanParser {
    fun formatCheck(text: String): Boolean
    fun getWeight(text: String): Int
    fun generateLeaf(
        text: String,
        selection: Int,
        isFocus: Boolean,
        styleTextOffsetMapping: StyleTextOffsetMapping
    ): StyleTextTree
}