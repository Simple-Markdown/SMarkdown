package indi.midreamsheep.app.tre.model.parser

import indi.midreamsheep.app.tre.model.render.TRERender
import indi.midreamsheep.app.tre.model.render.style.styletext.TREStyleTextTree

interface InlineParser {
    fun formatCheck(text: String): Boolean
    fun getWeight(text: String): Int
    fun generateLeaf(
        text: String,
        selection: Int,
        isFocus: Boolean,
        render: TRERender
    ): TREStyleTextTree
}