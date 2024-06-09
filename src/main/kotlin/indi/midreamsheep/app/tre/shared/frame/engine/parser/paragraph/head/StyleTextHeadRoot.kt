package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.head

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.shared.frame.engine.manager.block.TRECoreBlock
import indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.TRELineParser
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.offsetmap.TRERenderOffsetMap
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.leaf.TRECoreContentLeaf
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.root.TRECoreTreeRoot

class StyleTextHeadRoot(
    val level: Int,
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
    private val render: TRERender,
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
            isHidden = true
            render.offsetMap = object : TRERenderOffsetMap() {
                override fun getStartOffset(): Int {
                    return level + 1
                }
            }
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
        return if (isHidden) 0 else offset
    }

    override fun originalToTransformed(offset: Int): Int {
        return if (isHidden) 0 else offset
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