package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.latex

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.shared.api.display.DisplayFunction
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockDisplay
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockFocusData
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.OffsetCustomData
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.TRETextBlock
import indi.midreamsheep.app.tre.shared.tool.id.getIdFromPool
import indi.midreamsheep.app.tre.shared.tool.text.filter

class LatexBlock(
    context: TREEditorContext
): TRETextBlock(context) {
        private var isFocus = mutableStateOf(false)
        var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
        private var focusRequester: FocusRequester = FocusRequester()
        private lateinit var textLayoutResult: TextLayoutResult
        var xWindowStartPosition = 0f
        private val treBlockDisplay = object : TREBlockDisplay {
            override fun getDisplay() = DisplayFunction{
                Column(
                    Modifier.onGloballyPositioned {
                        xWindowStartPosition = it.localToWindow(Offset(0f, 0f)).x
                        treBlockComposeItemData.update(it)
                    }
                ) {
                    // 输入框
                    BasicTextField(
                        value = content.value,
                        onValueChange = { newValue ->
                            //TODO 设置内容 后续应用blockManager的命令进行调用修改
                            content.value = newValue
                        },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(5.dp)
                            .focusRequester(focusRequester)
                            .onPreviewKeyEvent {
                                if (it.type != KeyEventType.KeyDown) {
                                    return@onPreviewKeyEvent false
                                }
                                return@onPreviewKeyEvent context.treShortcutEvent.keyEvent()
                            }
                            .onGloballyPositioned {
                                xWindowStartPosition = it.localToWindow(Offset(0f, 0f)).x
                                treBlockComposeItemData.update(it)
                            },
                        onTextLayout = {
                            textLayoutResult = it
                        },
                    )
                    // 预览框
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ){
                        laTeXText(content)
                    }
                }
                LaunchedEffect(isFocus.value){
                    if (isFocus.value){
                        focusRequester.requestFocus()
                    }
                }
                LaunchedEffect(content.value){
                    //对shortcutState
                    shortcutState.isLeftAvailable = isStart()
                    shortcutState.isRightAvailable = isEnd()
                    shortcutState.isUpAvailable = textLayoutResult.getLineForOffset(content.value.selection.start) == 0
                    shortcutState.isDownAvailable = textLayoutResult.getLineForOffset(content.value.selection.start) == textLayoutResult.lineCount - 1
                    var offset = content.value.selection.start
                    if (offset > textLayoutResult.layoutInput.text.length) offset = textLayoutResult.layoutInput.text.length
                    left = textLayoutResult.getCursorRect(offset).left + xWindowStartPosition
                }
            }
        }

        override fun getTREBlockDisplay() = treBlockDisplay
        override fun getOutputContent() = content.value.text

        override fun setTextFieldValue(value: TextFieldValue) {
            content.value = value.filter()
        }

        override fun isStart() = content.value.selection.start == 0

        override fun isEnd() = content.value.selection.start == content.value.text.length-1

        fun focus(position: Int) {
            focusStandard()
            content.value = content.value.copy(
                selection = TextRange(position)
            )
        }

        override fun focusEvent(typeId: Long, data: TREBlockFocusData?) {
            when(typeId){
                getIdFromPool(OffsetCustomData::class.java) -> {
                    focusTransform((data as OffsetCustomData).offset)
                }
                else -> {focusStandard()}
            }
        }

        override fun getEditorShortcutState() = shortcutState
        override fun focusTransform(transformPosition: Int) = focus(transformPosition)
        override fun inTargetPositionUp(xPositionData: XPositionData) = focusX(xPositionData.x,false)
        override fun inTargetPositionDown(xPositionData: XPositionData) = focusX(xPositionData.x,true)
        private fun focusX(x: Float, isStart: Boolean) {
            val height = textLayoutResult.size.height
            val offsetForPosition = textLayoutResult.getOffsetForPosition(
                Offset(x - xWindowStartPosition, if(isStart) 0f else height.toFloat())
            )
            focusTransform(offsetForPosition)
        }

        override fun focusInEnd() {
            focus(content.value.text.length)
        }

        override fun focusInStart() {
            focus(0)
        }

        override fun focusStandard() {
            isFocus.value = true
        }

        override fun releaseFocus() {
            isFocus.value = false
        }

        override fun getTextFieldValue() = content.value
    }