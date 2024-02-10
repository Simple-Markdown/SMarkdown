package indi.midreamsheep.app.tre.model.editor.line.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRELine
import indi.midreamsheep.app.tre.model.editor.line.TRELineState
import indi.midreamsheep.app.tre.model.editor.line.TRETextLine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.render.TRELineParser
import indi.midreamsheep.app.tre.model.render.TREOffsetMappingAdapter
import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextOffsetMapping
import indi.midreamsheep.app.tre.model.render.styletext.leaf.TRECoreLeaf
import indi.midreamsheep.app.tre.model.render.styletext.root.TRECoreStyleTextRoot

class TRECoreLine(wrapper: TRELineState) :
    TRELine, TRETextLine {

    val parser = getBean(TRELineParser::class.java)
    var content: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue(""))
    var render: MutableState<TRETextRender> = mutableStateOf(
        TRETextRender(this).apply {
            styleTextTree = TRECoreStyleTextRoot().apply {
                addChildren(TRECoreLeaf("", TREStyleTextOffsetMapping(0, 0)))
            }
        }
    )

    private var wrapper: TRELineState? = wrapper

    private var selection = 0
    private var focusRequester: FocusRequester = FocusRequester()
    var isFocus = mutableStateOf(false)


    override fun focus() {
        isFocus.value = true
        wrapper!!.markdownLineInter.setCurrentMarkdownLineState(wrapper!!)
        content.value = content.value.copy(selection = TextRange(selection))
    }

    override fun releaseFocus() {
        isFocus.value = false
        selection = content.value.selection.start
    }

    override fun getComposable(context: TREEditorContext): @Composable () -> Unit {
        buildContent(wrapper!!.markdownLineInter)
        return {
            if (isFocus.value) {
                editorInput()
            } else {
                preview()
            }
        }
    }

    override fun getContent(): String {
        return content.value.text
    }

    @Composable
    fun editorInput() {
        BasicTextField(
            value = content.value,
            onValueChange = { newValue ->
                content.value = newValue
            },
            textStyle = TextStyle(
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocus.value = it.isFocused
                }
                .padding(top = 3.dp, bottom = 3.dp, start = 0.dp, end = 0.dp),
            visualTransformation = { text ->
                buildContent(wrapper!!.markdownLineInter)
                TransformedText(
                    text = render.value.styleTextTree!!.build(),
                    offsetMapping = TREOffsetMappingAdapter(render.value.styleTextTree!!),
                )
            },
            decorationBox = { innerTextField ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    render.value.prefixDecorations.forEach {
                        it.display()
                    }
                    Box{
                        innerTextField.invoke()
                        render.value.inlineDecorations.forEach {
                            it.display()
                        }
                    }
                    render.value.suffixDecorations.forEach {
                        it.display()
                    }
                }
            }
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    @Composable
    fun preview(){
        Column(
            Modifier.fillMaxWidth()
        ) {
            render.value.prefixDecorations.forEach {
                it.display()
            }
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                render.value.previewDisplay.display()
                render.value.inlineDecorations.forEach {
                    it.display()
                }
            }
            render.value.suffixDecorations.forEach {
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
        content.value = content.value.copy(selection = TextRange(0))
    }

    override fun getTextFieldValue(): TextFieldValue {
        return content.value
    }

    override fun setTextFieldValue(value: TextFieldValue) {
        content.value = value
    }

    private fun buildContent(
        context: TREStateManager
    ){
        render.value = parser.parse(content.value, this, context)
    }
}