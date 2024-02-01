import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import indi.midreamsheep.app.markdown.model.editor.parser.MarkdownParse
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.ui.main.mainPage
import java.io.File

@Composable
fun App() {
    MaterialTheme(
    ) {
        //editor(LocalTREFileManager(File("/home/midreamsheep/backgroud/test.md")))
        //settingPage()
        //fileChooser()
        mainPage()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val bean = getBean(MarkdownParse::class.java)
        App()
    }
}
