package indi.midreamsheep.app.tre.shared.frame.engine.context.block

import indi.midreamsheep.app.tre.shared.api.display.Display
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TRELinePreButton


/**
 * 用于管理block display的显示
 * */
interface TREBlockDisplay {
    fun getDisplay(): Display
    fun getPreButton(): TRELinePreButton
}