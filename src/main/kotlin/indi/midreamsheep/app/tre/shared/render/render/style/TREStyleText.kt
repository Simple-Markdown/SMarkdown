package indi.midreamsheep.app.tre.shared.render.render.style

import androidx.compose.foundation.text.InlineTextContent
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.render.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.render.block.TRECorePreview
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.TREStyleTextTree

class TREStyleText(line: TRECoreBlock) {
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
    var previewDisplay: Display = TRECorePreview(line)
    // 预览时对富文本的注解处理
    val previewAnnotation:MutableMap<String,InlineTextContent> = mutableMapOf()
    // 是否启动
    var preview:Boolean = true

    fun append(style: TREStyleText){
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

    fun isPreView() = (previewDisplay !is TRECorePreview || previewAnnotation.isNotEmpty()) && preview
}