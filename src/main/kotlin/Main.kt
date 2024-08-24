
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import indi.midreamsheep.app.tre.desktop.context.TREWindowContext
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.service.window.TREDesktopWindowService
import indi.midreamsheep.app.tre.service.window.run.TREApplicationRunCommandParser

val logger: org.slf4j.Logger = TRE_UI.getLogger()!!

fun main(args: Array<String>) = application {
    logger.info("args:{}",args)
    logger.info("Application start")
    logger.info("start to inject bean")
    val commandParser = getBean(TREApplicationRunCommandParser::class.java)
    logger.info("start to display window")
    commandParser.parse(args)
    App()
}

@Composable
fun App() {
    for (window in getBean(TREDesktopWindowService::class.java).snapshotStateList) {
        displayWindow(window.treWindowContext)
    }
}

@Composable
fun displayWindow(
    windowContext: TREWindowContext
){
    Window(
        onCloseRequest = { windowContext.close() },
        title = windowContext.getWindowTitle(),
        onPreviewKeyEvent = {
            val keyManager = windowContext.keyManager
            when(it.type){
                KeyEventType.KeyDown-> {
                    keyManager.add(it.key.keyCode)
                }
                KeyEventType.KeyUp-> {
                    keyManager.remove(it.key.keyCode)
                    return@Window true
                }
            }
            return@Window windowContext.previewKeyEvent(it)
        }
    ) {
        windowContext.getDisplay().getComposable().invoke()
    }
}