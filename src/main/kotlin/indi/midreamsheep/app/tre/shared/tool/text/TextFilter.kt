package indi.midreamsheep.app.tre.shared.tool.text

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

fun TextFieldValue.filter(): TextFieldValue {
    val count = this.text.count { it == '\t' }
    var selection = this.selection
    if(count!=0){
        selection = TextRange(
            start = this.selection.start + 3*count,
            end = this.selection.end + 3*count
        )
    }
    return this.copy(
        text = this.text.replace("\t", "    "),
        selection = selection
    )
}