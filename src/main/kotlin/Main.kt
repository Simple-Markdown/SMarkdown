
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.application
import indi.midreamsheep.app.tre.desktop.service.ioc.getBean
import indi.midreamsheep.app.tre.service.window.TREDesktopWindowService
import indi.midreamsheep.app.tre.service.window.run.TREApplicationRunCommandParser

val logger: org.slf4j.Logger = TRE.getLogger()!!

@Composable
fun App() {
    for (window in getBean(TREDesktopWindowService::class.java).snapshotStateList) {
        window.display.getComposable().invoke()
    }
}

fun main(args: Array<String>) = application {
    logger.info("args:{}",args)
    logger.info("Application start")
    logger.info("start to inject bean")
    val commandParser = getBean(TREApplicationRunCommandParser::class.java)
    logger.info("start to display window")
    commandParser.parse(args)
    App()
}