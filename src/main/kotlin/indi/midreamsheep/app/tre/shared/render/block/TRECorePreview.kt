package indi.midreamsheep.app.tre.shared.render.block

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextLayoutResult
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.render.render.style.TREStyleText

class TRECorePreview(val line: TRECoreBlock): Display {
    override fun getComposable():@Composable ()->Unit {
        return{
            var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
            val value = line.render.value.styleText.styleTextTree!!
            Text(
                text = value.getAnnotatedString().value!!,
                style = MaterialTheme.typography.bodyLarge,
                inlineContent = line.render.value.styleText.previewAnnotation,
                modifier = Modifier.fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures {
                                offset ->
                            layoutResult?.let {
                                val position = it.getOffsetForPosition(offset)
                                val stateManager = line.lineState.blockManager
                                stateManager.focusBlock(stateManager.indexOf(line.lineState)){ state ->
                                    (state.block as TRECoreBlock).focusTransform(position)
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

@Composable
fun treCoreDisplayStructure(
    render: TREStyleText,
    content: @Composable ()->Unit
){
    Column(
        Modifier.fillMaxWidth()
    ) {
        render.prefixLineDecorations.forEach {
            it.getComposable().invoke()
        }
        Box(Modifier.fillMaxWidth().height(IntrinsicSize.Max)) {
            render.backgroundDecorations.forEach {
                it.getComposable().invoke()
            }
            Row(
                Modifier.height(IntrinsicSize.Max)
            ) {
                render.prefixTextDecorations.forEach {
                    it.getComposable().invoke()
                }
                content.invoke()
                render.suffixTextDecorations.forEach {
                    it.getComposable().invoke()
                }
            }
        }
        render.suffixLineDecorations.forEach {
            it.getComposable().invoke()
        }
    }
}