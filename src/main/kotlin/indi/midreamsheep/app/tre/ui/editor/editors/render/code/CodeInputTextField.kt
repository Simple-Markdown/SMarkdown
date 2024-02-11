package indi.midreamsheep.app.tre.ui.editor.editors.render.code

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.render.parser.paragraph.code.editor.TRECodeLine

@Composable
fun codeInputTextField(
    codeLine:TRECodeLine,
    context: TREEditorContext
) {
    Column {
        BasicTextField(
            value = codeLine.content.value,
            onValueChange = { newValue ->
                codeLine.content.value = newValue
            },
            textStyle = TextStyle(
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(codeLine.focusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        codeLine.focus()
                    } else {
                        codeLine.releaseFocus()
                    }
                }
                .background(Color(0xFFF0F0F1))
                .padding(5.dp)
            ,
            visualTransformation = { text ->
                TransformedText(
                    text = buildAnnotatedString { append(codeLine.content.value)},
                    offsetMapping = OffsetMapping.Identity,
                )
            },
        ){
            Box{
                if (codeLine.content.value.isEmpty()) {
                    Text(
                        text = "一个很丑的代码块",
                        style = TextStyle(
                            fontSize = 15.sp,
                            textAlign = TextAlign.Start
                        )
                    )
                }
                it()
            }
        }
    }
    LaunchedEffect(codeLine.focus.value) {
        if (codeLine.focus.value) {
            codeLine.focusRequester.requestFocus()
        }
    }
}