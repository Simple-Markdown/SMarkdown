package indi.midreamsheep.app.tre.shared.frame.engine.block.context

import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockAbstract
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockComposeState
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREFocusEnum
import indi.midreamsheep.app.tre.shared.frame.engine.block.XPositionData

abstract class TREContextBlock(context: TREEditorContext): TREBlockAbstract(context) {

    public lateinit var innerContext:TREEditorContext

    fun context() = getBlockManager().getContext()

    /**
     * 默认调用当前块的光标位置
     * */
    override fun getComposeState() = object : TREBlockComposeState{
        override fun getPointerAbsoluteXPosition() = innerContext.blockManager.getCurrentBlock()!!.getComposeState().getPointerAbsoluteXPosition()
    }

    override fun focusInStart() {
        innerContext.blockManager.focusBlock(0, TREFocusEnum.IN_START)
    }

    override fun focusInEnd() {
        innerContext.blockManager.focusBlock(innerContext.blockManager.getSize()-1, TREFocusEnum.IN_END)
    }

    override fun focusStandard() {
        innerContext.blockManager.focusBlock(0, TREFocusEnum.STANDARD)
    }

    override fun inTargetPositionDown(xPositionData: XPositionData) {
        innerContext.blockManager.focusBlock(0, TREFocusEnum.IN_TARGET_POSITION_UP,xPositionData)
    }

    override fun inTargetPositionUp(xPositionData: XPositionData) {
        innerContext.blockManager.focusBlock(innerContext.blockManager.getSize()-1,
            TREFocusEnum.IN_TARGET_POSITION_DOWN,xPositionData)
    }


    override fun releaseFocus() {
        innerContext.blockManager.getCurrentBlock()?.releaseFocus()
        innerContext.blockManager.setCurrentBlock(null)
    }

}