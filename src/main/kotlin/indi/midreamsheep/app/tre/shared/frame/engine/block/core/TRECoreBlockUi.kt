package indi.midreamsheep.app.tre.shared.frame.engine.block.core

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.render.TREOffsetMappingAdapter
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.TREStyleTextLine


class TRECorePreviewUIModel(
    val render: MutableState<TRERender>
)

class TRECorePreviewUIEvent(
    val getTextLayoutResult: ()->TextLayoutResult,
    val resetTextLayoutResult: (TextLayoutResult)->Unit,
    val clickEvent: (Int)->Unit,
    val getAnnotatedString:()->MutableState<AnnotatedString>
)

/**
 * 用于核心的预览功能，用于预览抽象样式树
 * */
@Composable
fun TRECorePreview(
    model:TRECorePreviewUIModel,
    event:TRECorePreviewUIEvent
) {
    Text(
        text = event.getAnnotatedString().value,
        style = MaterialTheme.typography.bodyLarge,
        inlineContent = model.render.value.styleText.previewAnnotation,
        modifier = Modifier.fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures {
                    offset ->
                    event.clickEvent.invoke(event.getTextLayoutResult().getOffsetForPosition(offset))
                    }
            }
        ,
        onTextLayout = {
            event.resetTextLayoutResult.invoke(it)
        }
    )
}


class TRECoreTextFiledUIEvent(
    val onValueChange: (TextFieldValue)->Unit,
    val updateShortcutState:()->Unit,
    val requestFocus:()->Unit,
    val releaseFocus:()->Unit,
    val onTextLayoutChange:(TextLayoutResult)->Unit,
    val getAnnotatedString: () -> MutableState<AnnotatedString>
)

class TRECoreTextFiledUIModel(
    val focusRequester:FocusRequester,
    val content: MutableState<TextFieldValue>,
    val render: MutableState<TRERender>,
    val isFocus:MutableState<Boolean>
)

@Composable
fun TRECoreTextFiledUI(
    uiModel: TRECoreTextFiledUIModel,
    uiEvent: TRECoreTextFiledUIEvent
){
    val context = getEditorContext()
    var isInit by remember { mutableStateOf(false) }
    BasicTextField(
        value = uiModel.content.value,
        onValueChange = { newValue ->
            uiEvent.onValueChange(newValue)
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(uiModel.focusRequester)
            .onPreviewKeyEvent {
                if (it.type != KeyEventType.KeyDown) {
                    return@onPreviewKeyEvent false
                }
                return@onPreviewKeyEvent context.treShortcutEvent.keyEvent()
            }
            .onFocusChanged {
                if (!isInit) return@onFocusChanged
                if (!it.isFocused) uiEvent.releaseFocus()
            },
        visualTransformation = { _ ->
            TransformedText(
                text = uiEvent.getAnnotatedString().value,
                offsetMapping = TREOffsetMappingAdapter(uiModel.render.value.styleText.styleTextTree),
            )
        },
        onTextLayout = {
            uiEvent.onTextLayoutChange(it)
        },
        decorationBox = { innerTextField ->
            Column(modifier = Modifier.fillMaxWidth()) {
                treCoreDisplayStructure(uiModel.render.value.styleText) {
                    innerTextField.invoke()
                }
            }
        },
    )
    LaunchedEffect(uiModel.isFocus.value) {
        if(uiModel.isFocus.value){
            uiEvent.requestFocus()
            isInit = true
        }
    }
    LaunchedEffect(uiModel.content.value){
        uiEvent.updateShortcutState()
    }
}

/**
 * 用于对core显示进行规范化显示
 * */
@Composable
fun treCoreDisplayStructure(
    render: TREStyleTextLine,
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