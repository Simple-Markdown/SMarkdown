package indi.midreamsheep.app.tre.shared.render.block

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
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import indi.midreamsheep.app.tre.desktop.page.editor.TRELocalEditorWindow
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.model.editor.block.TREBlockAbstract
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.render.parser.paragraph.TRECoreLineParser
import indi.midreamsheep.app.tre.shared.render.render.TREOffsetMappingAdapter
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreTreeRoot
import indi.midreamsheep.app.tre.shared.tool.text.filter

class TRECoreBlock(
    state: TREBlockState
) : TREBlockAbstract(state), TRETextBlock {
    val parser = getBean(TRECoreLineParser::class.java)
    var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    var render: MutableState<TRERender> = mutableStateOf(
        TRERender(this).apply { styleText.styleTextTree = TRECoreTreeRoot().apply { addChild(TRECoreContentLeaf("")) } }
    )
    private var focusRequester: FocusRequester = FocusRequester()
    var isFocus = mutableStateOf(false)

    override fun getDisplay(): Display {
        buildContent()
        return Display{
            {
                if(render.value.offsetMap.check(content.value.selection.start)){
                   refresh( content.value.copy(selection = TextRange(render.value.offsetMap.resetOffset(content.value.selection.start))))
                }
                if(isFocus.value){
                    render.value.styleText.styleTextTree!!.reset(content.value.selection.start,content.value.selection.start,true)
                    editorInput()
                }else{
                    render.value.styleText.styleTextTree!!.reset(content.value.selection.start,content.value.selection.start,false)
                    preview()
                }
            }
        }
    }

    override fun getContent() = content.value.text

    @Composable
    fun editorInput() {
        val context = TRELocalEditorWindow.LocalContext.current
        BasicTextField(
            value = content.value,
            onValueChange = { newValue ->
                //如何内容没有改变则不执行操作
                if (content.value.text == newValue.text) {
                    refresh(newValue)
                    return@BasicTextField
                }
                //设置内容
                context.editorFileManager.getStateManager().executeOperator(
                    TREContentChange(content.value, newValue, context.editorFileManager.getStateManager().getTREBlockStateList().indexOf(lineState))
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onPreviewKeyEvent {
                    return@onPreviewKeyEvent render.value.listener.handleKeyEvent(it, context)
                }
            ,
            visualTransformation = { _ ->
                TransformedText(
                    text = render.value.styleText.styleTextTree!!.getAnnotatedString().value!!,
                    offsetMapping = TREOffsetMappingAdapter(render.value.styleText.styleTextTree!!),
                )
            },
            decorationBox = { innerTextField ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    treCoreDisplayStructure(render.value.styleText){
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
            render.value.styleText.styleTextTree!!.reset(newValue.selection.start,content.value.selection.start,isFocus.value)
        }
        content.value = newValue
        if(render.value.offsetMap.check(content.value.selection.start)){
            refresh( content.value.copy(selection = TextRange(render.value.offsetMap.resetOffset(content.value.selection.start))))
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
        render.value = parser.parse(textFieldValue.text, this)
        render.value.styleText.styleTextTree!!.reset(textFieldValue.selection.start,textFieldValue.selection.start,isFocus.value)
    }

    fun focus(position: Int) {
        focus()
        refresh(content.value.copy(selection = TextRange(position)))
    }

    override fun focus() { isFocus.value = true }

    fun focusTransform(position: Int) = focus(render.value.styleText.styleTextTree!!.transformedToOriginal(position))

    override fun focusFromLast() = focus(content.value.text.length)

    override fun focusFormStart() = focus(0)

    override fun releaseFocus() { isFocus.value = false }

    override fun getTextFieldValue() = content.value

    override fun getPreButton() = render.value.trePreButton

    override fun getTextFieldRange() = render.value.offsetMap
}