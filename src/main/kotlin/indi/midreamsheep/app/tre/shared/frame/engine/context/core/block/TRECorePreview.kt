package indi.midreamsheep.app.tre.shared.frame.engine.context.core.block

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import indi.midreamsheep.app.tre.shared.api.display.DisplayFunction
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.customdata.OffsetCustomData
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.TREStyleText

class TRECorePreview(val line: TRECoreBlock): DisplayFunction {

    @Composable
    override fun show() {
        val value = line.render.value.styleText.styleTextTree
        Text(
            text = value.getAnnotatedString().value!!,
            style = MaterialTheme.typography.bodyLarge,
            inlineContent = line.render.value.styleText.previewAnnotation,
            modifier = Modifier.fillMaxWidth()
                .onGloballyPositioned {
                    line.xWindowStartPosition = it.localToWindow(Offset.Zero).x
                }
                .pointerInput(Unit) {
                    detectTapGestures {
                            offset ->
                        line.textLayoutResult?.let {
                            val position = it.getOffsetForPosition(offset)
                            val stateManager = line.getBlockManager()
                            stateManager.focusBlock(stateManager.indexOf(line), OffsetCustomData(position))
                        }
                    }
                }
            ,
            onTextLayout = {
                line.textLayoutResult = it
            }
        )
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