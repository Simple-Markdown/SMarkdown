package indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block

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
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.observer.update
import indi.midreamsheep.app.tre.shared.frame.engine.getEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.TRECoreLineParser
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.TRELineParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TREOffsetMappingAdapter
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TRERangeInter
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot
import indi.midreamsheep.app.tre.shared.tool.text.filter

class TRECoreBlock(
    blockManager: TREBlockManager
) : TREBlockAbstract(blockManager), TRETextBlock {
    private val parser = getBean(TRECoreLineParser::class.java)
    private var decorateParser: TRELineParser? = null
    var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    var render: MutableState<TRERender> = mutableStateOf(
        TRERender(this).apply { styleText.styleTextTree = TRECoreTreeRoot().apply { addChild(TRECoreContentLeaf("")) } }
    )
    private var focusRequester: FocusRequester = FocusRequester()
    private var isFocus = mutableStateOf(false)

    override fun getDisplay(): Display {
        return Display{
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
    }

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
                    TREContentChange(content.value, newValue, context.blockManager.indexOf(this))
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onPreviewKeyEvent {
                    println("editor key")
                    if (it.type != KeyEventType.KeyDown) {
                        return@onPreviewKeyEvent false
                    }
                    return@onPreviewKeyEvent render.value.styleText.styleTextTree.keyEvent(
                        it,
                        context,
                        content.value.selection.start
                    )
                },
            visualTransformation = { _ ->
                TransformedText(
                    text = render.value.styleText.styleTextTree.getAnnotatedString().value!!,
                    offsetMapping = TREOffsetMappingAdapter(render.value.styleText.styleTextTree),
                )
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

    private fun buildContent(
        textFieldValue: TextFieldValue = content.value
    ){
        //去除上次的样式
        render.value.styleText.styleTextTree.remove()
        render.value = if(decorateParser==null){parser.parse(textFieldValue.text, this)}else{decorateParser!!.parse(textFieldValue.text,this)}
        render.value.styleText.styleTextTree.reset(textFieldValue.selection.start,textFieldValue.selection.start,isFocus.value)
        render.value.styleText.styleTextTree.insert()
        updateAllObserver()
    }

    fun focus(position: Int) {
        focus()
        refresh(content.value.copy(selection = TextRange(position)))
    }

    override fun focus() { isFocus.value = true }

    override fun focusTransform(transformPosition: Int) = focus(render.value.styleText.styleTextTree.transformedToOriginal(transformPosition))

    override fun focusFromLast() = focus(content.value.text.length)

    override fun focusFormStart() = focus(0)

    override fun releaseFocus() { isFocus.value = false }

    override fun getTextFieldValue() = content.value

    override fun getPreButton() = render.value.trePreButton

    fun setDecorateParser(parser: TRELineParser?){
        this.decorateParser = parser
        buildContent()
    }

    fun removeDecorateParser() = setDecorateParser(null)

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

    override fun getTextFieldRange() = object : TRERangeInter {
        override fun getStart(): Int {
            return render.value.styleText.styleTextTree.transformedToOriginal(0)
        }

        override fun getEnd(): Int {
            val styleTextTree = render.value.styleText.styleTextTree
            return styleTextTree.transformedToOriginal(styleTextTree.transformedSize())
        }
    }
}