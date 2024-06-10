package indi.midreamsheep.app.tre.shared.frame.engine.parser.span.call

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
import indi.midreamsheep.app.tre.api.annotation.render.inline.InLineParserList
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey
import indi.midreamsheep.app.tre.shared.frame.engine.render.TRERender
import indi.midreamsheep.app.tre.shared.frame.engine.render.style.styletext.TREStyleTextTreeInter
import lombok.extern.slf4j.Slf4j

/**
 * TODO 测试
 * */
@InLineParserList
@MapKey("C")
@Slf4j
class CallParser: indi.midreamsheep.app.tre.shared.frame.engine.parser.TREInlineStyleParser {

    override fun formatCheck(text: String): Boolean {
        if (text.length<4) return false
        if (text[0]=='C'&&text[1]=='a'&&text[2]=='l'&&text[3]=='l') return true
        return false
    }

    override fun getWeight(text: String): Int = 4

    override fun generateLeaf(
        text: String,
        render: TRERender
    ): TREStyleTextTreeInter {
        val id = SnowflakeGenerator().next()!!
        render.styleText.previewAnnotation[id.toString()] = InlineTextContent(
            Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
        ) {
            Image(
                imageVector = Icons.Default.Call,
                modifier = Modifier.fillMaxSize(),
                contentDescription = ""
            )
        }
        return StyleTextCallLeaf(
            true
            ,id
        )
    }
}