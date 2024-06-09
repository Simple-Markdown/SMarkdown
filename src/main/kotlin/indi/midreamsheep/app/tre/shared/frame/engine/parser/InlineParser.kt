package indi.midreamsheep.app.tre.shared.frame.engine.parser

import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TREStyleTextTreeInter

interface InlineParser {
    fun formatCheck(text: String): Boolean
    fun getWeight(text: String): Int
    fun generateLeaf(
        text: String,
        render: TRERender
    ): TREStyleTextTreeInter
}