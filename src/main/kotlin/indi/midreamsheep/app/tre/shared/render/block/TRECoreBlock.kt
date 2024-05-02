package indi.midreamsheep.app.tre.shared.render.block

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
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import indi.midreamsheep.app.tre.desktop.page.editor.context.TREEditorContext
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

class TRECoreBlock(
    lineState: TREBlockState
) : TREBlockAbstract(lineState), TRETextBlock {
    val parser = getBean(TRELineParser::class.java)
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
        lineState.markdownLineInter.getCurrentBlock()?.let {
            if (it != lineState) {
                it.line.releaseFocus()
            }
        } ?:
        lineState.markdownLineInter.setCurrentBlockState(lineState)
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
        lineState.markdownLineInter.getCurrentBlock()?.let {
            if (it == lineState) {
                lineState.markdownLineInter.setCurrentBlockState(null)
            }
        }
    }

    override fun getDisplay(context: TREEditorContext): Display {
        buildContent()
        return Display{
            {
                if(render.value.offsetMap.check(content.value.selection.start)){
                    content.value = content.value.copy(selection = TextRange(render.value.offsetMap.resetOffset(content.value.selection.start)))
                    buildContent()
                }
                if(isFocus.value){
                    editorInput(context)
                }else{
                    preview()
                }
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
                    val textRender = render.value
                    Column(
                        Modifier.fillMaxWidth()
                    ) {
                        textRender.styleText.prefixLineDecorations.forEach {
                            it.getComposable().invoke()
                        }
                        Box(Modifier.fillMaxWidth().height(IntrinsicSize.Max)) {
                            textRender.styleText.backgroundDecorations.forEach {
                                it.getComposable().invoke()
                            }
                            Row(
                                Modifier.height(IntrinsicSize.Max)
                            ) {
                                textRender.styleText.prefixTextDecorations.forEach {
                                    it.getComposable().invoke()
                                }
                                innerTextField.invoke()
                                textRender.styleText.suffixTextDecorations.forEach {
                                    it.getComposable().invoke()
                                }
                            }
                        }
                        textRender.styleText.suffixLineDecorations.forEach {
                            it.getComposable().invoke()
                        }
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
        val textRender = render.value
        Column(
            Modifier.fillMaxWidth()
        ) {
            textRender.styleText.prefixLineDecorations.forEach {
                it.getComposable().invoke()
            }
            Box(Modifier.fillMaxWidth().height(IntrinsicSize.Max)) {
                textRender.styleText.backgroundDecorations.forEach {
                    it.getComposable().invoke()
                }
                Row(
                    Modifier.height(IntrinsicSize.Max)
                ) {
                    textRender.styleText.prefixTextDecorations.forEach {
                        it.getComposable().invoke()
                    }
                    render.value.styleText.previewDisplay.getComposable().invoke()
                    textRender.styleText.suffixTextDecorations.forEach {
                        it.getComposable().invoke()
                    }
                }
            }
            textRender.styleText.suffixLineDecorations.forEach {
                it.getComposable().invoke()
            }
        }
    }

    override fun getTextFieldValue() = content.value

    override fun setTextFieldValue(value: TextFieldValue) {
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
        buildContent()
    }

    private fun buildContent(
        treStateManager: TREBlockManager = lineState.markdownLineInter,
        textFieldValue: TextFieldValue = content.value
    ){
        render.value = parser.parse(textFieldValue.text, textFieldValue.selection.start, this, treStateManager)
    }

    override fun getPreButton(): TRELinePreButton? {
        return render.value.trePreButton
    }

    override fun getTextFieldRange(): TRERenderOffsetMap {
        return render.value.offsetMap
    }
}