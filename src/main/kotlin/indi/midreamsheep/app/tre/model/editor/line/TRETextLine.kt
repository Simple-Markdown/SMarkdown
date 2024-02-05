package indi.midreamsheep.app.tre.model.editor.line

import androidx.compose.ui.text.input.TextFieldValue

interface TRETextLine {
    fun getTextFieldValue(): TextFieldValue
    fun setTextFieldValue(value: TextFieldValue)
}