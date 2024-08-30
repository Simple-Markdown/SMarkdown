package indi.midreamsheep.app.tre.shared.frame.engine.block.core

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.shared.api.display.DisplayFunction
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockDisplay
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockFocusData
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.OffsetCustomData
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.TRETextBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.treLineParse
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.tool.id.getIdFromPool
import indi.midreamsheep.app.tre.shared.tool.text.filter

class TRECoreBlock(
    context: TREEditorContext
) : TRETextBlock(context) {

    constructor(manager: TREBlockManager):this(manager.getContext())

    var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    var render: MutableState<TRERender> = mutableStateOf(
        TRERender().apply { styleText.styleTextTree = TRECoreTreeRoot().apply { addChild(TRECoreContentLeaf("")) } }
    )
    private var focusRequester: FocusRequester = FocusRequester()
    private var isFocus = mutableStateOf(false)
    private lateinit var textLayoutResult: TextLayoutResult
    var xWindowStartPosition = 0f

    private val treCoreTextFiledUIModel = TRECoreTextFiledUIModel(
        focusRequester = focusRequester,
        content = content,
        render = render,
        isFocus = isFocus
    )

    private val treCoreTextFiledUIEvent = TRECoreTextFiledUIEvent(
        onValueChange = {
            //如何内容没有改变则不执行操作
            if (content.value.text == it.text) {
                refresh(it)
                return@TRECoreTextFiledUIEvent
            }
            //设置内容
            context.blockManager.executeOperator(
                TREContentChange(content.value, it, this@TRECoreBlock)
            )
        },
        updateShortcutState = {
            //对shortcutState
            shortcutState.isLeftAvailable = isStart()
            shortcutState.isRightAvailable = isEnd()
            shortcutState.isUpAvailable = textLayoutResult.getLineForOffset(content.value.selection.start) == 0
            shortcutState.isDownAvailable = textLayoutResult.getLineForOffset(content.value.selection.start) == textLayoutResult.lineCount - 1
        },
        requestFocus = {focusRequester.requestFocus()},
        releaseFocus = {releaseFocus()},
        onTextLayoutChange = {
            textLayoutResult = it
            var offset =
                render.value.styleText.styleTextTree.originalToTransformed(content.value.selection.start)
            if (offset > it.layoutInput.text.length) offset = it.layoutInput.text.length
            left = it.getCursorRect(offset).left + xWindowStartPosition
        },
        getAnnotatedString = {
            return@TRECoreTextFiledUIEvent render.value.styleText.styleTextTree.getAnnotatedString(content.value.selection.start,content.value.selection.start,true)
        }
    )

    private val treCorePreviewUIModel = TRECorePreviewUIModel(render)

    private val treCorePreviewUIEvent = TRECorePreviewUIEvent(
        getTextLayoutResult = {return@TRECorePreviewUIEvent textLayoutResult},
        resetTextLayoutResult = {textLayoutResult = it},
        clickEvent = {
            getBlockManager().focusBlock(
                getBlockManager().indexOf(this),
                OffsetCustomData(it), getIdFromPool(OffsetCustomData::class.java)
            )
        },
        getAnnotatedString = {
            return@TRECorePreviewUIEvent render.value.styleText.styleTextTree.getAnnotatedString(content.value.selection.start,content.value.selection.start,false)
        }
    )

    private val treBlockDisplay = object : TREBlockDisplay {
        override fun getDisplay() = DisplayFunction{
            Box(
                Modifier.onGloballyPositioned {
                    xWindowStartPosition = it.localToWindow(Offset(0f, 0f)).x
                    treBlockComposeItemData.update(it)
                }
            ){
                if(render.value.styleText.styleTextTree.check(content.value.selection.start)){
                    refresh( content.value.copy(selection = TextRange(render.value.styleText.styleTextTree.resetPosition(content.value.selection.start))))
                }
                treCoreDisplayStructure(
                    render.value.styleText,
                ){
                    if(isFocus.value){

                        TRECoreTextFiledUI(treCoreTextFiledUIModel,treCoreTextFiledUIEvent)
                    }else{
                        if(render.value.styleText.previewDisplay==null){
                            TRECorePreview(treCorePreviewUIModel,treCorePreviewUIEvent)
                        }else{
                            render.value.styleText.previewDisplay!!.invoke()
                        }
                    }
                }
            }
        }

        override fun getPreButton() = render.value.trePreButton
    }

    /**
     * 获取用于显示实体类
     * */
    override fun getTREBlockDisplay() = treBlockDisplay


    override fun getOutputContent() = content.value.text

    override fun whenInsert() {
        buildContent()
    }
    override fun whenRemove() {}

    private fun refresh(newValue: TextFieldValue) {
        //如何光标未改变就不执行操作
        if(newValue.selection.start == content.value.selection.start&&newValue.selection.end == content.value.selection.end){
            return
        }
        content.value = newValue
        if(render.value.styleText.styleTextTree.check(content.value.selection.start)){
            refresh( content.value.copy(selection = TextRange(render.value.styleText.styleTextTree.resetPosition(content.value.selection.start))))
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
        render.value.styleText.styleTextTree.insert()
        //updateAllObserver()
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
    override fun focusTransform(transformPosition: Int) = focus(render.value.styleText.styleTextTree.transformedToOriginal(transformPosition))
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
    
    private fun updateAllObserver(){
        TODO()
    }
}
