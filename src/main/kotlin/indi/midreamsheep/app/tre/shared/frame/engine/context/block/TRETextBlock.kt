package indi.midreamsheep.app.tre.shared.frame.engine.context.block

import androidx.compose.ui.text.input.TextFieldValue
import cn.hutool.core.util.IdUtil
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager

/**
 * 展示文本的抽象块
 * 例如： 核心文本渲染的核心块，代码框等纯文本
 * */
abstract class TRETextBlock(blockManager: TREBlockManager): TREBlockAbstract(blockManager) {
    abstract fun getShortcutState():ShortcutState
    abstract fun getTextFieldValue(): TextFieldValue
    abstract fun setTextFieldValue(value: TextFieldValue)
    abstract fun isStart(): Boolean
    abstract fun isEnd(): Boolean
    abstract fun focusTransform(transformPosition: Int)
}

val offsetFocus = IdUtil.getSnowflakeNextId()

class ShortcutState{
    var isUpAvailable = false
    var isDownAvailable = false
    var isLeftAvailable = false
    var isRightAvailable = false
    var left = 0.0f
    override fun toString(): String {
        return "ShortcutState(isUpAvailable=$isUpAvailable, isDownAvailable=$isDownAvailable, isLeftAvailable=$isLeftAvailable, isRightAvailable=$isRightAvailable, left=$left)"
    }


}
