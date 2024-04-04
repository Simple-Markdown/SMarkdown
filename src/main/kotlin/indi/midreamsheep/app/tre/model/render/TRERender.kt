package indi.midreamsheep.app.tre.model.render

import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.render.listener.TREDefaultRenderListener
import indi.midreamsheep.app.tre.model.render.listener.TRERenderListener
import indi.midreamsheep.app.tre.model.render.offsetmap.TRERenderDefaultOffsetMap
import indi.midreamsheep.app.tre.model.render.offsetmap.TRERenderOffsetMap
import indi.midreamsheep.app.tre.model.render.style.TRETextStyle

class TRERender(
    private val line:TRECoreBlock,
) {
    // 渲染的样式文本树
    var styleText = TRETextStyle(line)
    // 专属快建键监听
    var listener: TRERenderListener = TREDefaultRenderListener()
    // 块域
    var offsetMap: TRERenderOffsetMap = TRERenderDefaultOffsetMap()
}