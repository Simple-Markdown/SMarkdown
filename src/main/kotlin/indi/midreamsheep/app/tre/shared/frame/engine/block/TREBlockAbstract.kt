package indi.midreamsheep.app.tre.shared.frame.engine.block;

import indi.midreamsheep.app.tre.shared.frame.TREEditorContext

abstract class TREBlockAbstract(protected var context: TREEditorContext) : TREBlock {

    protected val shortcutState = TREBlockShortcutState()
    protected var treBlockComposeItemData: TREBlockComposeItemData = TREBlockComposeItemData()

    override fun getEditorShortcutState() = shortcutState

    override fun getEditorContext() = context

    override fun resetEditorContext(treEditorContext: TREEditorContext) {
        context = treEditorContext
    }


    override fun focus(typeId: Long, data: TREBlockFocusData) {
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
    open fun focusEvent(typeId: Long, data: TREBlockFocusData?) = focusStandard()
    abstract fun focusInStart()
    abstract fun focusInEnd()
    abstract fun focusStandard()
    abstract fun inTargetPositionDown(xPositionData: XPositionData)
    abstract fun inTargetPositionUp(xPositionData: XPositionData)
}