package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.latex

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.text.input.TextFieldValue
import org.scilab.forge.jlatexmath.ParseException
import org.scilab.forge.jlatexmath.TeXConstants
import org.scilab.forge.jlatexmath.TeXFormula
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.imageio.ImageIO


@Composable
fun laTeXText(latex: MutableState<TextFieldValue>) {
    var imageBimapState by remember { mutableStateOf<ImageBitmap?>(null) }
    val scrollState = rememberScrollState()
    Column(Modifier.fillMaxWidth()){
        Row(
            Modifier.fillMaxWidth().horizontalScroll(scrollState),
        ) {
            Spacer(Modifier.weight(1f))
            if (imageBimapState==null){
                Text("loading")
            }else{
                androidx.compose.foundation.Image(
                    bitmap = imageBimapState!!,
                    contentDescription = "latex"
                )
            }
            Spacer(Modifier.weight(1f))
        }
        HorizontalScrollbar(
            adapter = ScrollbarAdapter(scrollState),
            modifier = Modifier
        )
    }
    LaunchedEffect(latex.value){
        val formula = getFormula(latex.value.text) ?: return@LaunchedEffect
        val createBufferedImage = formula.createBufferedImage(TeXConstants.STYLE_DISPLAY, 20f, Color.BLACK, Color.WHITE)
        imageBimapState =
            org.jetbrains.skia.Image.makeFromEncoded(convertImageToStream(createBufferedImage).toByteArray())
                .toComposeImageBitmap()
    }
}

fun getFormula(
    text:String
):TeXFormula?{
    try {
        val teXFormula = TeXFormula(text)
        return teXFormula
    }catch (_:ParseException){
        return null
    }
}

@Throws(IOException::class)
fun convertImageToStream(image: Image): ByteArrayOutputStream {
    // 创建一个BufferedImage
    val bufferedImage = BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB)
    bufferedImage.graphics.drawImage(image, 0, 0, null)

    // 将BufferedImage写入ByteArrayOutputStream
    val outputStream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage, "jpg", outputStream)

    return outputStream
}