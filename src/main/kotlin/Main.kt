import androidx.compose.runtime.Composable
import androidx.compose.ui.window.application
import indi.midreamsheep.app.tre.model.editor.parser.MarkdownParse
import indi.midreamsheep.app.tre.api.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.app.viewmodel.pojo.TREWindow
import indi.midreamsheep.app.tre.ui.app.WindowDisplay
import indi.midreamsheep.app.tre.ui.mainpage.MainPageWindow
import indi.midreamsheep.app.tre.ui.setting.SettingWindow

val logger: org.slf4j.Logger = TRE.getLogger()!!
val treAppContext = TREAppContext()

@Composable
fun App() {
    for (window in treAppContext.windowViewModel.windows) {
        window.windowDisplay.display()
    }
}

fun main() = application {
    logger.info("Application start")
    logger.info("start to inject bean")
    getBean(MarkdownParse::class.java)
    logger.info("start to display window")
    treAppContext.windowAction.registerWindow(
        TREWindow(
            windowDisplay = MainPageWindow(),
            name = "MainWindow"
        )
    )
    App()
}
