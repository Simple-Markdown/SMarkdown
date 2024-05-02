package indi.midreamsheep.app.tre.shared.render.block

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.model.editor.block.TREBlock
import indi.midreamsheep.app.tre.shared.render.render.offsetmap.TRERenderOffsetMap

interface TRETextBlock: TREBlock {
    fun getTextFieldValue(): TextFieldValue
    fun setTextFieldValue(value: TextFieldValue)
    fun getTextFieldRange(): TRERenderOffsetMap
}