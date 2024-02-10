package indi.midreamsheep.app.tre.model.render

import androidx.compose.foundation.text.InlineTextContent
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreDisplay
import indi.midreamsheep.app.tre.model.editor.line.core.TRECoreLine
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextTree

class TRETextRender(line:TRECoreLine) {
    // 样式文本树
    var styleTextTree:TREStyleTextTree? = null
    // 文本前缀修饰组
    val prefixDecorations:MutableList<Display> = mutableListOf()
    // 文本后缀修饰组
    val suffixDecorations:MutableList<Display> = mutableListOf()
    // 预览时对富文本的注解处理
    val inlineDecorations:MutableList<Display> = mutableListOf()
    // 文本预览
    var previewDisplay:Display = TRECoreDisplay(line)
    // 预览时对富文本的注解处理
    val previewAnnotation:MutableMap<String,InlineTextContent> = mutableMapOf()
}