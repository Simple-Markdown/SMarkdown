package indi.midreamsheep.app.tre.model.render

import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.render.listener.TREDefaultRenderListener
import indi.midreamsheep.app.tre.model.render.listener.TRERenderListener
import indi.midreamsheep.app.tre.model.render.style.TRETextStyle

class TRERender(
    private val line:TRECoreLine,
) {
    // 渲染的样式文本树
    var styleText = TRETextStyle(line)
    // 专属快建键监听 TODO
    var listener: TRERenderListener = TREDefaultRenderListener()
    // 前置组件 TODO
    // 右键专属菜单 TODO
}