package indi.midreamsheep.app.tre.model.render.parser

import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextOffsetMapping
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextTree

interface SpanParser {
    fun formatCheck(text: String): Boolean
    fun getWeight(text: String): Int
    fun generateLeaf(
        text: String,
        selection: Int,
        isFocus: Boolean,
        styleTextOffsetMapping: TREStyleTextOffsetMapping,
        render: TRETextRender
    ): TREStyleTextTree
}