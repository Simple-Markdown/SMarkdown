package indi.midreamsheep.app.tre.shared.frame.engine.manager.block

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.shared.frame.engine.render.offsetmap.TRERenderOffsetMap

interface TRETextBlock: TREBlock {
    fun getTextFieldValue(): TextFieldValue
    fun setTextFieldValue(value: TextFieldValue)
    fun getTextFieldRange(): TRERenderOffsetMap
}