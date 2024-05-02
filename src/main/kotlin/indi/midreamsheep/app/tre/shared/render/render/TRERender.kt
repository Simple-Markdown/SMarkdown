package indi.midreamsheep.app.tre.shared.render.render

import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.render.listener.TREDefaultRenderListener
import indi.midreamsheep.app.tre.shared.render.render.listener.TRERenderListener
import indi.midreamsheep.app.tre.shared.render.render.offsetmap.TRERenderOffsetMap
import indi.midreamsheep.app.tre.shared.render.render.prebutton.TREDefaultLinePreButton
import indi.midreamsheep.app.tre.shared.render.render.prebutton.TRELinePreButton
import indi.midreamsheep.app.tre.shared.render.render.style.TREStyleText

class TRERender(
    private val line: TRECoreBlock,
) {
    // 渲染的样式文本树
    var styleText = TREStyleText(line)
    // 快捷键监听
    var listener: TRERenderListener = TREDefaultRenderListener()
    // 用于限制光标的可达域
    var offsetMap: TRERenderOffsetMap = TRERenderOffsetMap()
    // 前置按钮
    var trePreButton: TRELinePreButton = TREDefaultLinePreButton()
}