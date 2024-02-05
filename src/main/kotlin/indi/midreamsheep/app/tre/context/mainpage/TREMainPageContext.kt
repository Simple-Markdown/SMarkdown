package indi.midreamsheep.app.tre.context.mainpage

import indi.midreamsheep.app.tre.context.TREContext
import indi.midreamsheep.app.tre.context.mainpage.action.RightPageAction
import indi.midreamsheep.app.tre.context.mainpage.viewmodel.RightPageViewModel
import indi.midreamsheep.app.tre.context.mainpage.viewmodel.SideBarButtonViewModel
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

/**
 * 主页面的上下文
 * */

class TREMainPageContext : TREContext {
    /** 左侧点击的按钮的管理器 */
    val sideBarButtonViewModel = SideBarButtonViewModel()
    /** 右侧显示的内容的viewModel */
    val rightPageViewModel = RightPageViewModel()

    /** 右侧显示内容的事件 */
    val rightPageAction = RightPageAction(this)
}