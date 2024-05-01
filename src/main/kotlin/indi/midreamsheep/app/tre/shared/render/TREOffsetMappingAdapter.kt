package indi.midreamsheep.app.tre.shared.render

import androidx.compose.ui.text.input.OffsetMapping
import indi.midreamsheep.app.tre.shared.render.style.styletext.TREStyleTextTree

/**
 * 偏移量映射
 * 对styleTextTree的装饰器
 * */
class TREOffsetMappingAdapter(private val styleTextTree: TREStyleTextTree) : OffsetMapping {

    override fun originalToTransformed(offset: Int) = styleTextTree.originalToTransformed(offset)

    override fun transformedToOriginal(offset: Int) = styleTextTree.transformedToOriginal(offset)

}