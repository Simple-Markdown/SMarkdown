package indi.midreamsheep.app.tre.context.app.action

import indi.midreamsheep.app.tre.context.TREAction
import indi.midreamsheep.app.tre.context.app.TREAppContext
import indi.midreamsheep.app.tre.context.app.viewmodel.pojo.TREWindow
import logger

/**
 * 窗口相关的action
 * */
class WindowAction(context: TREAppContext): TREAction<TREAppContext>(context){

    /**
     * 注册一个窗口进行显示
     * */
    fun registerWindow(
        window: TREWindow
    ) {
        if (window.windowDisplay.close==null){
            window.windowDisplay.close = {
                logger.info("close window:{}",window.name)
                removeWindow(window)
            }
        }
        context.windowViewModel.windows.add(
            window
        )
    }

    /**
     * 移除一个窗口
     * */
    fun removeWindow(
        window: TREWindow
    ) {
        context.windowViewModel.windows.remove(window)
    }
}