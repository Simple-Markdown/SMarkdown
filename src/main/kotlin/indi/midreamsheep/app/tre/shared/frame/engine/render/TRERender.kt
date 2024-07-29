package indi.midreamsheep.app.tre.shared.frame.engine.render

import indi.midreamsheep.app.tre.shared.frame.engine.block.core.TRECoreBlock
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
    // 前置按钮
    var trePreButton: TRELinePreButton = TREDefaultLinePreButton()
}