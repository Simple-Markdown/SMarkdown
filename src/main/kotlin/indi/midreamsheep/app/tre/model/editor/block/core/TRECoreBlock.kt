package indi.midreamsheep.app.tre.model.editor.block.core

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.block.TREBlockAbstract
import indi.midreamsheep.app.tre.model.editor.block.TREBlockState
import indi.midreamsheep.app.tre.model.editor.block.TRETextBlock
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.render.TREOffsetMappingAdapter
import indi.midreamsheep.app.tre.model.render.TRERender
import indi.midreamsheep.app.tre.model.render.style.styletext.leaf.TRECoreLeaf
import indi.midreamsheep.app.tre.model.render.style.styletext.root.TRECoreStyleTextRoot
import indi.midreamsheep.app.tre.tool.ioc.getBean

class TRECoreBlock(
    lineState: TREBlockState
) : TREBlockAbstract(lineState),TRETextBlock {

    val parser = getBean(indi.midreamsheep.app.tre.model.parser.paragraph.TRELineParser::class.java)
    var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
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
    val propertySet = mutableSetOf<Long>()

    override fun focus() {
        isFocus.value = true
        lineState.markdownLineInter.setCurrentBlockState(lineState)
    }

    override fun releaseFocus() {
        isFocus.value = false
        lineState.markdownLineInter.getCurrentBlock()?.let {
            if (it == lineState) {
                lineState.markdownLineInter.setCurrentBlockState(null)
            }
        }
    }

    override fun getDisplay(context: TREEditorContext): Display {
        setTextFieldValue(content.value)
        return Display{
            if(content.value.selection.start < render.value.offsetMap.getStartOffset()){
                content.value = content.value.copy(selection = TextRange(render.value.offsetMap.getStartOffset()))
            }
            if (isFocus.value) {
                editorInput(context)
            } else {
                preview()
            }
        }
    }

    override fun getContent() = content.value.text

    @Composable
    fun editorInput(
        context: TREEditorContext
    ) {
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
                .onFocusChanged {
                    isFocus.value = it.isFocused
                }
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
                    coreComposable(render.value) {
                        innerTextField()
                    }
                    if (render.value.styleText.isPreView()) {
                        preview()
                    }
                }
            },
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            context.editorFileManager.getStateManager().setCurrentBlockState(lineState)
        }
    }

    @Composable
    fun preview(){
        coreComposable(render.value) {
            render.value.styleText.previewDisplay.display()
        }
    }

    @Composable
    fun coreComposable(
        textRender: TRERender,
        content: @Composable () -> Unit
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            textRender.styleText.prefixLineDecorations.forEach {
                it.display()
            }
            Box(Modifier.fillMaxWidth().height(IntrinsicSize.Max)) {
                textRender.styleText.backgroundDecorations.forEach {
                    it.display()
                }
                Row(
                    Modifier.height(IntrinsicSize.Max)
                ) {
                    textRender.styleText.prefixTextDecorations.forEach {
                        it.display()
                    }
                    content.invoke()
                    textRender.styleText.suffixTextDecorations.forEach {
                        it.display()
                    }
                }
            }
            textRender.styleText.suffixLineDecorations.forEach {
                it.display()
            }
        }
    }

    override fun focusFromLast() {
        focus()
        val length = content.value.text.length
        content.value = content.value.copy(selection = TextRange(length))
    }

    override fun focusFormStart() {
        focus()
        content.value = content.value.copy(selection = TextRange(render.value.offsetMap.getStartOffset()))
    }

    override fun getTextFieldValue() = content.value

    override fun setTextFieldValue(value: TextFieldValue) {
        //处理制表符
        val count = value.text.count { it == '\t' }
        var selection = value.selection
        if(count!=0){
            selection = TextRange(
                start = value.selection.start + 3*count,
                end = value.selection.end + 3*count
            )
        }
        content.value = value.copy(
            text = value.text.replace("\t", "    "),
            selection = selection
        )
        buildContent(lineState.markdownLineInter)
    }

    fun buildContent(
        treStateManager: TREStateManager,
        textFieldValue: TextFieldValue = content.value
    ){
        render.value = parser.parse(textFieldValue.text, textFieldValue.selection.start, this, treStateManager)
    }
}