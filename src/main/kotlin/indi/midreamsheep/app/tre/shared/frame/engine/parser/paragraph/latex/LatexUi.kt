package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.latex

import androidx.compose.foundation.layout.Box
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
fun laTeXText(latex: MutableState<TextFieldValue>, modifier: Modifier = Modifier) {
    val formula = getFormula(latex.value.text)
    if (formula == null) {
        Text("illegal content")
        return
    }
    val createBufferedImage = formula.createBufferedImage(TeXConstants.STYLE_DISPLAY, 20f, Color.BLACK, Color.WHITE)
    val imageBitmap: ImageBitmap =
        org.jetbrains.skia.Image.makeFromEncoded(convertImageToStream(createBufferedImage).toByteArray())
            .toComposeImageBitmap()

    Box{
        androidx.compose.foundation.Image(
            bitmap = imageBitmap,
            contentDescription = "latex"
        )
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