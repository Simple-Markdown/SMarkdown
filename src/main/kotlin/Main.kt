
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.application
import indi.midreamsheep.app.tre.tool.ioc.getBean
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.app.viewmodel.pojo.TREWindow
import indi.midreamsheep.app.tre.model.render.TRELineParser
import indi.midreamsheep.app.tre.ui.mainpage.MainPageWindow

val logger: org.slf4j.Logger = TRE.getLogger()!!

@Composable
fun App() {
    for (window in TREAppContext.context.windowViewModel.windows) {
        window.windowDisplay.display()
    }
}

fun main(args: Array<String>) = application {
    logger.info("传入的参数:{}",args)
    logger.info("Application start")
    logger.info("start to inject bean")
    getBean(TRELineParser::class.java)
    logger.info("start to display window")
    TREAppContext.context.windowAction.registerWindow(
        TREWindow(
            windowDisplay = MainPageWindow(),
            name = "MainWindow"
        )
    )
    App()
}
