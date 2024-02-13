package indi.midreamsheep.app.tre.context.mainpage

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.mainpage.viewmodel.MainPageButtonViewModel

/**
 * 主页面的上下文
 * */

class TREMainPageContext : TREContext {
    /** 左侧点击的按钮的管理器 */
    val mainPageButtonViewModel = MainPageButtonViewModel(this)

}