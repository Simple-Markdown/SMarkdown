package indi.midreamsheep.app.tre.model.editor.block

import androidx.compose.ui.text.input.TextFieldValue

interface TRETextBlock: TREBlock {
    fun getTextFieldValue(): TextFieldValue
    fun setTextFieldValue(value: TextFieldValue)
}