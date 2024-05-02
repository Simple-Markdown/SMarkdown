package indi.midreamsheep.app.tre.desktop.page.main.context

import indi.midreamsheep.app.tre.shared.api.context.TREContext
import indi.midreamsheep.app.tre.desktop.page.main.context.viewmodel.MainPageButtonViewModel

/**
 * 主页面的上下文
 * */

class TREMainPageContext : TREContext {
    /** 左侧点击的按钮的管理器 */
    val mainPageButtonViewModel = MainPageButtonViewModel(this)

}