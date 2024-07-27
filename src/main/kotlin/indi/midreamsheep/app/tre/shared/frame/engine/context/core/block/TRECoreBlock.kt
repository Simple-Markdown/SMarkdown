package indi.midreamsheep.app.tre.shared.frame.engine.context.core.block

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.ShortcutState
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TREBlockDisplay
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.context.block.observer.update
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.parser.treLineParse
import indi.midreamsheep.app.tre.shared.frame.engine.render.TREOffsetMappingAdapter
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot
import indi.midreamsheep.app.tre.shared.tool.text.filter

class TRECoreBlock(
    blockManager: TREBlockManager
) : TRETextBlock(blockManager) {
    var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    var render: MutableState<TRERender> = mutableStateOf(
        TRERender(this).apply { styleText.styleTextTree = TRECoreTreeRoot().apply { addChild(TRECoreContentLeaf("")) } }
    )
    private var focusRequester: FocusRequester = FocusRequester()
    private var isFocus = mutableStateOf(false)
    private val shortcutState = ShortcutState()
    var textLayoutResult: TextLayoutResult? = null
    var xWindowStartPosition = 0f

    private val treBlockDisplay = object : TREBlockDisplay{
        override fun getDisplay() = Display{
            {
                if(render.value.styleText.styleTextTree.check(content.value.selection.start)){
                    refresh( content.value.copy(selection = TextRange(render.value.styleText.styleTextTree.resetPosition(content.value.selection.start))))
                }
                if(isFocus.value){
                    render.value.styleText.styleTextTree.reset(content.value.selection.start,content.value.selection.start,true)
                    editorInput()
                }else{
                    render.value.styleText.styleTextTree.reset(content.value.selection.start,content.value.selection.start,false)
                    preview()
                }
            }
        }

        override fun getPreButton() = render.value.trePreButton
    }

    /**
     * 获取用于显示实体类
     * */
    override fun getTREBlockDisplay() = treBlockDisplay


    override fun getContent() = content.value.text

    override fun whenInsert() {
        buildContent()
    }
    override fun whenRemove() {}

    @Composable
    fun editorInput() {
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
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onPreviewKeyEvent {
                    if (it.type != KeyEventType.KeyDown) {
                        return@onPreviewKeyEvent false
                    }
                    return@onPreviewKeyEvent context.treShortcutEvent.keyEvent()
                }
                .onGloballyPositioned {
                    xWindowStartPosition = it.localToWindow(Offset(0f, 0f)).x
                },
            visualTransformation = { _ ->
                TransformedText(
                    text = render.value.styleText.styleTextTree.getAnnotatedString().value!!,
                    offsetMapping = TREOffsetMappingAdapter(render.value.styleText.styleTextTree),
                )
            },
            onTextLayout = {
                textLayoutResult = it
                //对shortcutState
                shortcutState.isLeftAvailable = isStart()
                shortcutState.isRightAvailable = isEnd()
                shortcutState.isUpAvailable = it.getLineForOffset(content.value.selection.start) == 0
                shortcutState.isDownAvailable = it.getLineForOffset(content.value.selection.start) == it.lineCount - 1
                var offset =
                    render.value.styleText.styleTextTree.originalToTransformed(content.value.selection.start)
                if (offset > it.layoutInput.text.length) offset = it.layoutInput.text.length
                shortcutState.left = it.getCursorRect(offset).left + xWindowStartPosition
            },
            decorationBox = { innerTextField ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    treCoreDisplayStructure(render.value.styleText) {
                        innerTextField.invoke()
                    }
                    if (render.value.styleText.isPreView()) {
                        preview()
                    }
                }
            },
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    private fun refresh(newValue: TextFieldValue) {
        //如何光标未改变就不执行操作
        if(newValue.selection.start == content.value.selection.start&&newValue.selection.end == content.value.selection.end){
            return
        }
        if(newValue.selection.end != content.value.selection.end){
            render.value.styleText.styleTextTree.reset(newValue.selection.start,content.value.selection.start,isFocus.value)
        }
        content.value = newValue
        if(render.value.styleText.styleTextTree.check(content.value.selection.start)){
            refresh( content.value.copy(selection = TextRange(render.value.styleText.styleTextTree.resetPosition(content.value.selection.start))))
        }
    }

    @Composable
    fun preview(){
        treCoreDisplayStructure(
            render.value.styleText,
        ){
            render.value.styleText.previewDisplay.getComposable().invoke()
        }
    }

    override fun setTextFieldValue(value: TextFieldValue) {
        content.value = value.filter()
        buildContent()
    }

    override fun isStart() = render.value.styleText.styleTextTree.transformedToOriginal(0)==content.value.selection.start

    override fun isEnd() = render.value.styleText.styleTextTree.transformedToOriginal(render.value.styleText.styleTextTree.transformedSize()) == content.value.selection.start

    private fun buildContent(
        textFieldValue: TextFieldValue = content.value
    ){
        //去除上次的样式
        render.value.styleText.styleTextTree.remove()
        render.value = treLineParse(textFieldValue.text,this)
        render.value.styleText.styleTextTree.reset(textFieldValue.selection.start,textFieldValue.selection.start,isFocus.value)
        render.value.styleText.styleTextTree.insert()
        updateAllObserver()
    }

    fun focus(position: Int) {
        focus()
        refresh(content.value.copy(selection = TextRange(position)))
    }

    override fun focus() { isFocus.value = true }
    override fun getShortcutState() = shortcutState

    override fun focusTransform(transformPosition: Int) = focus(render.value.styleText.styleTextTree.transformedToOriginal(transformPosition))
    override fun focusX(x: Float, isStart: Boolean) {
        if (textLayoutResult==null){
            return
        }
        val height = textLayoutResult!!.size.height
        val offsetForPosition = textLayoutResult!!.getOffsetForPosition(
            Offset(x - xWindowStartPosition, if(isStart) 0f else height.toFloat())
        )
        focusTransform(offsetForPosition)
    }

    override fun focusFromLast() = focus(content.value.text.length)

    override fun focusFromStart() = focus(0)

    override fun releaseFocus() {
        isFocus.value = false
    }

    override fun getTextFieldValue() = content.value

    /**
     * 每次当前文本修改时触发对所有观察者通知变化
     * */
    private fun updateAllObserver(){
        val blockManager = getBlockManager()
        update(
            blockManager.indexOf(this),
            blockManager,
            render.value.styleText.styleTextTree.getTypeId()
        )
    }
}
