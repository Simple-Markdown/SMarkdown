package indi.midreamsheep.app.tre.model.editor.parser.impl.paragraph.divide

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.tre.model.editor.line.core.CoreTRELine
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.editor.parser.parser.ParagraphParser
import indi.midreamsheep.app.tre.model.styletext.TREStyleTextTree
import indi.midreamsheep.app.tre.model.styletext.leaf.TRECoreLeaf
import indi.midreamsheep.app.tre.model.styletext.pojo.TREStyleTextOffsetMapping
import indi.midreamsheep.app.tre.model.styletext.root.TRECoreStyleTextRoot
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@MapInjector(target = "paragraph",key="-")
@Comment
class DivideParser: ParagraphParser {

    override fun formatCheck(text: String): Boolean {
        val content = text.trim()
        if (content.length<3) return false
        if (content[1]!='-'||content[2]!='-'||content[0]!='-') return false
        //如果之后是空格或者换行符，那么就是分割线
        if (content.length==3) return true
        val pointer = 3
        for (i in pointer until content.length) {
            if (content[i]!=' '&&content[i]!='\n'&&content[i]!='-'){
                return false
            }
        }
        return true
    }

    override fun getAnnotatedString(
        text: TextFieldValue,
        stateList: TREStateManager,
        state: CoreTRELine,
        recall: () -> Unit
    ): Pair<TREStyleTextTree, Display> {
        val apply = TRECoreLeaf(
            content = text.text,
            offsetMapping = TREStyleTextOffsetMapping(0, 0)
        )
        val tree = TRECoreStyleTextRoot()
        tree.addChildren(apply)
        return Pair(tree, Display {
            Column(Modifier.fillMaxWidth().height(20.dp).clickable{recall.invoke()}) {
                Divider()
            }
        })
    }

    override fun getWeight(text: String) = 3
}