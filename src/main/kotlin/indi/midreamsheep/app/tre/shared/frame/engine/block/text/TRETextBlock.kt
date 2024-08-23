package indi.midreamsheep.app.tre.shared.frame.engine.block.text

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockAbstract
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockComposeState
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager

/**
 * 展示文本的抽象块
 * 例如： 核心文本渲染的核心块，代码框等纯文本
 * */
abstract class TRETextBlock(context: TREEditorContext): TREBlockAbstract(context) {
    protected var left = 0f
    private val composeState = object : TREBlockComposeState{

        /**
         * 获取光标的绝对位置
         * */
        override fun getPointerAbsoluteXPosition() = left

    }

    /**
     * 获取block组件状态，包含内部的矩阵信息
     **/
    override fun getComposeState() = composeState

    abstract fun getTextFieldValue(): TextFieldValue
    abstract fun setTextFieldValue(value: TextFieldValue)
    abstract fun isStart(): Boolean
    abstract fun isEnd(): Boolean
    abstract fun focusTransform(transformPosition: Int)
    constructor(manager: TREBlockManager):this(manager.getContext())
}


