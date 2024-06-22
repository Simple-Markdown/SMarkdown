package indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block

import androidx.compose.ui.text.input.TextFieldValue

interface TRETextBlock: TREBlock {
    fun getTextFieldValue(): TextFieldValue
    fun setTextFieldValue(value: TextFieldValue)
    fun isStart(): Boolean
    fun isEnd(): Boolean
    fun focusFromLast()
    fun focusFromStart()
    fun focusTransform(transformPosition: Int)
}