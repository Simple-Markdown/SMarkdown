package indi.midreamsheep.app.tre.shared.render

import indi.midreamsheep.app.tre.model.editor.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.listener.TREDefaultRenderListener
import indi.midreamsheep.app.tre.shared.render.listener.TRERenderListener
import indi.midreamsheep.app.tre.shared.render.offsetmap.TRERenderOffsetMap
import indi.midreamsheep.app.tre.shared.render.prebutton.TREDefaultLinePreButton
import indi.midreamsheep.app.tre.shared.render.prebutton.TRELinePreButton
import indi.midreamsheep.app.tre.shared.render.style.TRETextStyle

class TRERender(
    private val line: TRECoreBlock,
) {
    // 渲染的样式文本树
    var styleText = TRETextStyle(line)
    // 快捷键监听
    var listener: TRERenderListener = TREDefaultRenderListener()
    // 用于限制光标的可达域
    var offsetMap: TRERenderOffsetMap = TRERenderOffsetMap()
    // 前置按钮
    var trePreButton: TRELinePreButton = TREDefaultLinePreButton()
}