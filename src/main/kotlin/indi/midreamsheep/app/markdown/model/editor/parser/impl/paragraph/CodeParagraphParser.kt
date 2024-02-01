package indi.midreamsheep.app.markdown.model.editor.parser.impl.paragraph

import androidx.compose.runtime.Composable
import indi.midreamsheep.app.markdown.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager
import indi.midreamsheep.app.markdown.model.editor.parser.ParagraphParser
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@MapInjector(target = "paragraph",key="`")
@Comment
class CodeParagraphParser: ParagraphParser {
    override fun formatCheck(text: String): Boolean {
        if (text.length<3) return false
        if (text[0]!='`') return false
        if (text[1]!='`') return false
        if (text[2]!='`') return false
        return true
    }

    override fun getComposable(
        text: String,
        recall: () -> Unit,
        stateList: indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager,
        state: indi.midreamsheep.app.markdown.model.editor.line.core.CoreTRELine
    ):@Composable () -> Unit {
        /*//获取之后的附加内容
        state.type = "CODE"
        state.typeData = text.substring(3)
        state.content.value = ""
        //行内解析
        state.inputComposable = {
            state,stateList->
            codeInputTextField(state,stateList)
        }
        state.previewComposable = {
            state,_->
            Text(
                text = state.content.value
            )
        }*/
        state.isFocused.value = true
        return {}
    }

    override fun analyse(texts: List<String>, lineNumber: Int, state: indi.midreamsheep.app.markdown.model.editor.manager.TREStateManager): Int {
        return lineNumber+1
    }
}