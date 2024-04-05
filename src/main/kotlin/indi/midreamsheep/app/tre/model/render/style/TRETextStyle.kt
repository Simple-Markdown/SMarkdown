package indi.midreamsheep.app.tre.model.render.style

import androidx.compose.foundation.text.InlineTextContent
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.block.core.TRECoreDisplay
import indi.midreamsheep.app.tre.model.render.style.styletext.TREStyleTextTree

class TRETextStyle(line:TRECoreBlock) {
    // 样式文本树
    var styleTextTree: TREStyleTextTree? = null
    // 文本前缀修饰组,上一行修饰
    val prefixLineDecorations:MutableList<Display> = mutableListOf()
    // 文本前缀修饰组,文字前修饰
    val prefixTextDecorations:MutableList<Display> = mutableListOf()
    // 文本后缀修饰组，下一行修饰
    val suffixLineDecorations:MutableList<Display> = mutableListOf()
    // 文本后缀修饰组，文字后修饰
    val suffixTextDecorations:MutableList<Display> = mutableListOf()
    // 背景修饰
    val backgroundDecorations:MutableList<Display> = mutableListOf()
    // 文本预览
    var previewDisplay:Display = TRECoreDisplay(line)
    // 预览时对富文本的注解处理
    val previewAnnotation:MutableMap<String,InlineTextContent> = mutableMapOf()
    // 是否启动
    var preview:Boolean = true

    fun append(style: TRETextStyle){
        styleTextTree?.addChildren(style.styleTextTree!!)
        prefixLineDecorations.addAll(style.prefixLineDecorations)
        prefixTextDecorations.addAll(style.prefixTextDecorations)
        suffixLineDecorations.addAll(style.suffixLineDecorations)
        suffixTextDecorations.addAll(style.suffixTextDecorations)
        backgroundDecorations.addAll(style.backgroundDecorations)
        previewDisplay = style.previewDisplay
        previewAnnotation.putAll(style.previewAnnotation)
        preview = style.preview||preview
    }

    fun isPreView() = (previewDisplay !is TRECoreDisplay || previewAnnotation.isNotEmpty()) && preview
}