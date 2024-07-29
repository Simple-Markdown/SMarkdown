package indi.midreamsheep.app.tre.shared.frame.engine.block.text

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockAbstract
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager

/**
 * 展示文本的抽象块
 * 例如： 核心文本渲染的核心块，代码框等纯文本
 * */
abstract class TRETextBlock(blockManager: TREBlockManager): TREBlockAbstract(blockManager) {
    abstract fun getShortcutState(): ShortcutState
    abstract fun getTextFieldValue(): TextFieldValue
    abstract fun setTextFieldValue(value: TextFieldValue)
    abstract fun isStart(): Boolean
    abstract fun isEnd(): Boolean
    abstract fun focusTransform(transformPosition: Int)
}


