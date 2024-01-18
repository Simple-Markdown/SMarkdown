import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import indi.midreamsheep.app.markdown.editor.manager.Test
import indi.midreamsheep.app.markdown.editor.parser.MarkdownParse
import indi.midreamsheep.app.markdown.tool.context.getBean
import indi.midreamsheep.app.markdown.ui.editor.editor

@Composable
@Preview
fun App() {
    MaterialTheme {
        editor(Test())
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val bean = getBean(MarkdownParse::class.java)
        App()
    }
}
