package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.head

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Backspace
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.model.editor.operator.core.TREContentChange
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.TRELineParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot

class StyleTextHeadRoot(
    private val level: Int,
): TRECoreTreeRoot() {

    override fun generateAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = (15+(6-level+1)*5).sp,
                )
            ) {
                for (child in getChildren()) {
                    append(child.getAnnotatedString().value)
                }
            }
        }
    }
}

class StyleTextHeadPrefix(
    private val level: Int,
    private val empty: Boolean,
): TRECoreTreeRoot() {

    private var isHidden = false

    override fun generateAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = (15+(6-level+1)*5).sp,
                    color = Color.Gray
                )
            ) {
                if (!isDisplay()) {
                    append("#".repeat(level) + " ")
                }
            }
        }
    }

    private fun isDisplay(): Boolean {
        if (selection > level  || !isEdit) {
            isHidden = true&&empty
        }
        return isHidden
    }

    override fun transformedSize(): Int {
        return if (isHidden) 0 else level + 1
    }

    override fun originalSize(): Int {
        return level + 1
    }

    override fun transformedToOriginal(offset: Int): Int {
        return if (isHidden) level + 1  else offset
    }

    override fun originalToTransformed(offset: Int): Int {
        return if (isHidden) 0 else offset
    }

    override fun check(position: Int): Boolean {
        if(isHidden){
            return position < level+1
        }
        return false
    }

    override fun resetPosition(position: Int) = level+1

    override fun keyEvent(context: TREEditorContext, position: Int): Boolean {
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Backspace.keyCode))){
            val treCoreBlock = context.blockManager.getCurrentBlock()!! as TRECoreBlock
            if (treCoreBlock.isStart()){
                val index = treCoreBlock.content.value.selection.start - (level + 1)
                val text = treCoreBlock.content.value.text.substring(level+1)
                context.blockManager.executeOperator(
                    TREContentChange(
                        treCoreBlock.content.value,
                        treCoreBlock.content.value.copy(
                            text = text,
                            selection = TextRange(index)
                        ),
                        treCoreBlock
                    )
                )
                return true
            }
        }
        return false
    }
}

class StyleTextCrossHeadRoot(
    private val lastBlock: TRECoreBlock
): TRECoreTreeRoot() {

    override fun generateAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = Color.Gray
                )
            ){
                getChildren().forEach {
                    append(it.getAnnotatedString().value)
                }
            }
        }
    }

    override fun insert() {
        lastBlock.setDecorateParser(object : TRELineParser {
                override fun parse(text: String, block: TRECoreBlock): TRERender {
                    val render = TRERender(block)
                    render.styleText.styleTextTree = StyleTextHeadRoot(2).apply {
                        addChild(
                            TRECoreContentLeaf(text)
                        )
                    }
                    return render
                }
            }
        )
    }

    override fun remove() {
        lastBlock.removeDecorateParser()
    }

}