package indi.midreamsheep.app.tre.shared.frame.engine.render

import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TREDefaultLinePreButton
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TRELinePreButton
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.TREStyleTextLine

/**
 * 用于核心block的行渲染格式
 * */
class TRERender {
    // 渲染的样式文本树
    var styleText = TREStyleTextLine()
    // 前置按钮
    var trePreButton: TRELinePreButton = TREDefaultLinePreButton()
}