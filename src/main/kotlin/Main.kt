import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import indi.midreamsheep.app.markdown.editor.manager.core.LocalMarkdownFileManager
import indi.midreamsheep.app.markdown.editor.parser.MarkdownParse
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.ui.editor.editor
import indi.midreamsheep.app.markdown.ui.file.fileChooser
import java.io.File

@Composable
fun App() {
    MaterialTheme {
        //editor(LocalMarkdownFileManager(File("/home/midreamsheep/backgroud/test.md")))
        //settingPage()
        fileChooser()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val bean = getBean(MarkdownParse::class.java)
        App()
    }
}
