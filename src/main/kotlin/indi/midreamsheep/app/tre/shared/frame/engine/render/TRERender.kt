package indi.midreamsheep.app.tre.shared.frame.engine.render

import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.render.listener.TREDefaultRenderListener
import indi.midreamsheep.app.tre.shared.frame.engine.render.listener.TRERenderListener
import indi.midreamsheep.app.tre.shared.frame.engine.render.offsetmap.TRERenderOffsetMap
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TREDefaultLinePreButton
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TRELinePreButton
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.TREStyleText

/**
 * 用于核心block的行渲染格式
 * */
class TRERender(
    private val line: TRECoreBlock,
) {
    // 渲染的样式文本树
    var styleText = TREStyleText(line)
    // TODO 将其统一到文本树中
    var listener: TRERenderListener = TREDefaultRenderListener()
    // TODO 统一到文本树中
    var offsetMap: TRERenderOffsetMap = TRERenderOffsetMap()
    // TODO 根据文本树进行渲染
    var trePreButton: TRELinePreButton = TREDefaultLinePreButton()
}