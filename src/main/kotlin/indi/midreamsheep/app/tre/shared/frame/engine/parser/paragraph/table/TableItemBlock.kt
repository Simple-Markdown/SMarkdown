package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.table

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.shared.api.display.DisplayFunction
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockDisplay
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockFocusData
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.OffsetCustomData
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.treInlineParse
import indi.midreamsheep.app.tre.shared.frame.engine.render.TREOffsetMappingAdapter
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TREStyleTextTree
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot
import indi.midreamsheep.app.tre.shared.tool.id.getIdFromPool
import indi.midreamsheep.app.tre.shared.tool.text.filter

class TableItemBlock(
    context:TREEditorContext,
    private val isHeader:Boolean,
    initContent:String
): TRETextBlock(context) {
    private var isFocus = mutableStateOf(false)
    var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(initContent))
    var treStyleTextTreeInter: MutableState<TREStyleTextTree> = mutableStateOf(buildContent())
    private var focusRequester: FocusRequester = FocusRequester()
    private lateinit var textLayoutResult: TextLayoutResult
    private var xWindowStartPosition = 0f
    private val treBlockDisplay = object : TREBlockDisplay {
        override fun getDisplay() = DisplayFunction{}
    }

    override fun getTREBlockDisplay() = treBlockDisplay
    override fun getOutputContent() = content.value.text

    @Composable
    fun getComposable(
        modifier: Modifier
    ):@Composable ()->Unit{
        return{
            val styleTree = treStyleTextTreeInter.value
            if(isFocus.value){
                editorInput(modifier)
            }else{
                preview(modifier)
            }
        }
    }

    @Composable
    fun editorInput(modifier: Modifier) {
        val context = getEditorContext()
        BasicTextField(
            value = content.value,
            onValueChange = { newValue ->
                //如何内容没有改变则不执行操作
                if (content.value.text == newValue.text) {
                    refresh(newValue)
                    return@BasicTextField
                }
                //设置内容
                context.blockManager.executeOperator(
                    TREContentChange(content.value, newValue, this)
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = if(isHeader){FontWeight.Bold}else{FontWeight.Normal}
            ),
            modifier = modifier
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
            visualTransformation = { _ ->
                TransformedText(
                    text = treStyleTextTreeInter.value.getAnnotatedString(content.value.selection.start,content.value.selection.start,true).value,
                    offsetMapping = TREOffsetMappingAdapter(treStyleTextTreeInter.value),
                )
            },
            onTextLayout = {
                textLayoutResult = it
                var offset =
                    treStyleTextTreeInter.value.originalToTransformed(content.value.selection.start)
                if (offset > it.layoutInput.text.length) offset = it.layoutInput.text.length
                left = it.getCursorRect(offset).left + xWindowStartPosition
            },
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        LaunchedEffect(content.value){
            //对shortcutState
            shortcutState.isLeftAvailable = isStart()
            shortcutState.isRightAvailable = isEnd()
            shortcutState.isUpAvailable = textLayoutResult.getLineForOffset(content.value.selection.start) == 0
            shortcutState.isDownAvailable = textLayoutResult.getLineForOffset(content.value.selection.start) == textLayoutResult.lineCount - 1
        }
    }

    private fun refresh(newValue: TextFieldValue) {
        //如何光标未改变就不执行操作
        if(newValue.selection.start == content.value.selection.start&&newValue.selection.end == content.value.selection.end){
            return
        }
        content.value = newValue
        if(treStyleTextTreeInter.value.check(content.value.selection.start)){
            refresh( content.value.copy(selection = TextRange(treStyleTextTreeInter.value.resetPosition(content.value.selection.start))))
        }
    }

    @Composable
    fun preview(modifier: Modifier) {
        val value = treStyleTextTreeInter.value
        Text(
            text = value.getAnnotatedString(content.value.selection.start,content.value.selection.start,true).value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isHeader){ FontWeight.Bold }else{ FontWeight.Normal },
            modifier = modifier
                .padding(5.dp)
                .onGloballyPositioned {
                    xWindowStartPosition = it.localToWindow(Offset.Zero).x
                    treBlockComposeItemData.update(it)
                }
                .pointerInput(Unit) {
                    detectTapGestures {
                            offset ->
                        val position = textLayoutResult.getOffsetForPosition(offset)
                        val stateManager = getBlockManager()
                        // 上下文的焦点事件传播，获取若有父上下文，则向上传递
                        stateManager.focusBlock(
                            stateManager.indexOf(this@TableItemBlock),
                            OffsetCustomData(position), getIdFromPool(OffsetCustomData::class.java)
                        )
                    }
                }
            ,
            onTextLayout = {
                textLayoutResult = it
            }
        )
    }

    override fun setTextFieldValue(value: TextFieldValue) {
        content.value = value.filter()
         treStyleTextTreeInter.value = buildContent()
    }

    override fun isStart() = treStyleTextTreeInter.value.transformedToOriginal(0)==content.value.selection.start

    override fun isEnd() = treStyleTextTreeInter.value.transformedToOriginal(treStyleTextTreeInter.value.transformedSize()) == content.value.selection.start

    private fun buildContent(
        textFieldValue: TextFieldValue = content.value
    ):TREStyleTextTree{
        val value = TRECoreTreeRoot().apply {
            addChildren(treInlineParse(textFieldValue.text).toTypedArray())
        }
        return value
    }

    fun focus(position: Int) {
        focusStandard()
        refresh(content.value.copy(selection = TextRange(position)))
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
    override fun focusTransform(transformPosition: Int) = focus(treStyleTextTreeInter.value.transformedToOriginal(transformPosition))
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