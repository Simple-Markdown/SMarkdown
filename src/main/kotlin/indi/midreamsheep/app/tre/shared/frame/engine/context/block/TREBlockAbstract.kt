package indi.midreamsheep.app.tre.shared.frame.engine.context.block;

import indi.midreamsheep.app.tre.shared.frame.engine.context.core.customdata.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager

abstract class TREBlockAbstract(private var blockManager: TREBlockManager) : TREBlock {
    override fun getBlockManager() = blockManager
    override fun setBlockManager(blockManager: TREBlockManager) {this.blockManager = blockManager}
    override fun focus(typeId: Long, data: CustomData) {
        when(typeId){
            TREFocusEnum.IN_END.id -> {
                focusInEnd()
            }
            TREFocusEnum.IN_START.id -> {
                focusInStart()
            }
            TREFocusEnum.IN_TARGET_POSITION_UP.id -> {
                inTargetPositionUp(data as XPositionData)
            }
            TREFocusEnum.IN_TARGET_POSITION_DOWN.id -> {
                inTargetPositionDown(data as XPositionData)
            }
            TREFocusEnum.STANDARD.id -> {
                focusStandard()
            }
            else->{
                focusEvent(typeId, data)
            }
        }
    }
    open fun focusEvent(typeId: Long, data: CustomData?) = focusStandard()
    abstract fun focusInStart()
    abstract fun focusInEnd()
    abstract fun focusStandard()
    abstract fun inTargetPositionDown(xPositionData: XPositionData)
    abstract fun inTargetPositionUp(xPositionData: XPositionData)
}