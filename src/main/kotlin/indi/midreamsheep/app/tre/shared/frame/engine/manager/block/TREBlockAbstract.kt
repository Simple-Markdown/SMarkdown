package indi.midreamsheep.app.tre.shared.frame.engine.manager.block;

import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TREDefaultLinePreButton
import indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton.TRELinePreButton

abstract class TREBlockAbstract(private var lineState: TREBlockState) : TREBlock {

    /**
     * 获取前置按钮
     */
    override fun getPreButton(): TRELinePreButton {
        return TREDefaultLinePreButton()
    }

    override fun getLineState() = lineState

    override fun focusFormStart() = focus()

    override fun focusFromLast() = focus()
}