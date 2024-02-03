import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import indi.midreamsheep.app.markdown.model.editor.parser.MarkdownParse
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.ui.main.mainPage

val logger: org.slf4j.Logger = TRE.getLogger()!!

@Composable
fun App() {
    MaterialTheme(
    ) {
        mainPage()
    }
}

fun main() = application {
    logger.info("Application start")
    logger.info("start to inject bean")
    getBean(MarkdownParse::class.java)
    logger.info("start to display window")
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
