package indi.midreamsheep.app.tre.model.render.parser.span.call

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.sp
import cn.hutool.core.lang.generator.SnowflakeGenerator
import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParser
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.model.render.TRETextRender
import indi.midreamsheep.app.tre.model.render.parser.SpanParser
import indi.midreamsheep.app.tre.model.render.styletext.TREStyleTextTree
import lombok.extern.slf4j.Slf4j

/**
 * TODO 测试
 * */
@InLineParser
@MapKey("C")
@Slf4j
class CallParser: SpanParser {

    override fun formatCheck(text: String): Boolean {
        if (text.length<4) return false
        if (text[0]=='C'&&text[1]=='a'&&text[2]=='l'&&text[3]=='l') return true
        return false
    }

    override fun getWeight(text: String): Int = 4

    override fun generateLeaf(
        text: String,
        selection: Int,
        isFocus: Boolean,
        render: TRETextRender
    ): TREStyleTextTree {
        val id = SnowflakeGenerator().next()!!
        render.previewAnnotation[id.toString()] = InlineTextContent(
            Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
        ) {
            Image(
                imageVector = Icons.Default.Call,
                modifier = Modifier.fillMaxSize(),
                contentDescription = ""
            )
        }
        return StyleTextCallLeaf(
            !isFocus,
            id
        )
    }
}