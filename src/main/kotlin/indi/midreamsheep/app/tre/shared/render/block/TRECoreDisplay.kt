package indi.midreamsheep.app.tre.shared.render.block

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextLayoutResult
import indi.midreamsheep.app.tre.shared.api.display.Display

class TRECoreDisplay(val line: TRECoreBlock): Display {
    override fun getComposable():@Composable ()->Unit {
        return{
            var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
            val value = line.render.value.styleText.styleTextTree!!
            Text(
                text = value.build(false),
                style = MaterialTheme.typography.bodyLarge,
                inlineContent = line.render.value.styleText.previewAnnotation,
                modifier = Modifier.fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures {
                                offset ->
                            layoutResult?.let {
                                val position = it.getOffsetForPosition(offset)
                                val stateManager = line.lineState.markdownLineInter
                                stateManager.focusBlock(stateManager.indexOf(line.lineState)){ state ->
                                    (state.line as TRECoreBlock).focusTransform(position)
                                }
                            }
                        }
                    }
                ,
                onTextLayout = { layoutResult = it }
            )
        }

    }
}