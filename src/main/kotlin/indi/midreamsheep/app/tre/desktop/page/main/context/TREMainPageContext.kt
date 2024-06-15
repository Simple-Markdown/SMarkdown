package indi.midreamsheep.app.tre.desktop.page.main.context

import indi.midreamsheep.app.tre.desktop.context.TREWindowContext
import indi.midreamsheep.app.tre.desktop.page.main.context.viewmodel.MainPageButtonViewModel

/**
 * 主页面的上下文
 * */

class TREMainPageContext : TREWindowContext {
    /** 左侧点击的按钮的管理器 */
    val mainPageButtonViewModel = MainPageButtonViewModel(this)

}