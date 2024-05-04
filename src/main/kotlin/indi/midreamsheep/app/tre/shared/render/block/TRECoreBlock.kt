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
import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.render.parser.paragraph.TRELineParser
import indi.midreamsheep.app.tre.shared.render.render.TREOffsetMappingAdapter
import indi.midreamsheep.app.tre.shared.render.render.TRERender
import indi.midreamsheep.app.tre.shared.render.render.offsetmap.TRERenderOffsetMap
import indi.midreamsheep.app.tre.shared.render.render.prebutton.TRELinePreButton
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.leaf.TRECoreLeaf
import indi.midreamsheep.app.tre.shared.render.render.style.styletext.root.TRECoreStyleTextRoot
import indi.midreamsheep.app.tre.shared.tool.text.filter

class TRECoreBlock(
    state: TREBlockState
) : TREBlockAbstract(state), TRETextBlock {
    val parser = getBean(TRELineParser::class.java)
    var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    private var oldValue: TextFieldValue = TextFieldValue("")
    var render: MutableState<TRERender> = mutableStateOf(
        TRERender(this).apply {
            styleText.styleTextTree = TRECoreStyleTextRoot().apply {
                addChildren(TRECoreLeaf(""))
            }
        }
    )
    private var focusRequester: FocusRequester = FocusRequester()
    var isFocus = mutableStateOf(false)
    //维护一张属性表，用于存储属性
    val propertySet = HashSet<Long>()

    override fun focus() {
        isFocus.value = true
    }

    fun focus(position: Int) {
        focus()
        content.value = content.value.copy(selection = TextRange(position))
    }

    fun focusTransform(position: Int) = focus(render.value.styleText.styleTextTree!!.transformedToOriginal(position))

    override fun focusFromLast() = focus(content.value.text.length)

    override fun focusFormStart() = focus(0)

    override fun releaseFocus() {
        isFocus.value = false
    }

    override fun getDisplay(): Display {
        buildContent()
        return Display{
            {
                if(render.value.offsetMap.check(content.value.selection.start)){
                    content.value = content.value.copy(selection = TextRange(render.value.offsetMap.resetOffset(content.value.selection.start)))
                    buildContent()
                }
                if(isFocus.value){
                    editorInput()
                }else{
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
                if (content.value.text == newValue.text) {
                    setTextFieldValue(newValue)
                    return@BasicTextField
                }
                context.editorFileManager.getStateManager().executeOperator(
                    TREContentChange(
                        content.value,
                        newValue,
                        context.editorFileManager.getStateManager().getTREBlockStateList().indexOf(lineState),
                    )
                )

            },
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onPreviewKeyEvent {
                    return@onPreviewKeyEvent render.value.listener.handleKeyEvent(it, context)
                },
            visualTransformation = { _ ->
                TransformedText(
                    text = render.value.styleText.styleTextTree!!.build(true),
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

    @Composable
    fun preview(){
        treCoreDisplayStructure(
            render.value.styleText,
        ){
            render.value.styleText.previewDisplay.getComposable().invoke()
        }
    }

    override fun getTextFieldValue() = content.value

    override fun setTextFieldValue(value: TextFieldValue) {
        content.value = value.filter()
        buildContent()
    }

    private fun buildContent(
        treStateManager: TREBlockManager = lineState.blockManager,
        textFieldValue: TextFieldValue = content.value
    ){
        if(oldValue == textFieldValue){
            return
        }
        render.value = parser.parse(textFieldValue.text, textFieldValue.selection.start, this, treStateManager)
        oldValue = textFieldValue
    }

    override fun getPreButton(): TRELinePreButton {
        return render.value.trePreButton
    }

    override fun getTextFieldRange(): TRERenderOffsetMap {
        return render.value.offsetMap
    }
}