package indi.midreamsheep.app.tre.context.mainpage.action

import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.context.mainpage.TREMainPageContext

class RightPageAction(val context: TREMainPageContext) {
    fun setRightPageContent(
        display: Display
    ) {
        context.rightPageViewModel.rightPageViewState.value = display
    }
}