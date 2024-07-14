package indi.midreamsheep.app.tre.shared.frame.engine.context.block

import androidx.compose.ui.text.input.TextFieldValue
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.customdata.OffsetCustomData
import indi.midreamsheep.app.tre.shared.frame.engine.context.core.customdata.XPositionData
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts.BackspaceShortcut
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts.DirectionLeftShortcut
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts.DirectionRightShortcut
import indi.midreamsheep.app.tre.shared.frame.engine.listener.shortcut.shortcuts.DownShortcut
import indi.midreamsheep.app.tre.shared.tool.id.getIdFromPool

/**
 * 展示文本的抽象块
 * 例如： 核心文本渲染的核心块，代码框等纯文本
 * */
abstract class TRETextBlock(blockManager: TREBlockManager): TREBlockAbstract(blockManager) {
    override fun focus(typeId: Long, data: CustomData) {
        when(typeId){
            getIdFromPool(XPositionData::class.java) -> {
                focusX((data as XPositionData).x,false)
            }
            getIdFromPool(DownShortcut::class.java) -> {
                if (data==CustomData.NONE){
                    focus()
                    return
                }
                focusX((data as XPositionData).x,true)
            }
            getIdFromPool(OffsetCustomData::class.java) -> {
                focusTransform((data as OffsetCustomData).offset)
            }
            getIdFromPool(BackspaceShortcut::class.java) -> {
                focusFromLast()
            }
            getIdFromPool(DirectionRightShortcut::class.java) -> {
                focusFromStart()
            }
            getIdFromPool(DirectionLeftShortcut::class.java) -> {
                focusFromLast()
            }
            else->{
                focus()
            }
        }
    }
    abstract fun getShortcutState():ShortcutState
    abstract fun focus()
    abstract fun getTextFieldValue(): TextFieldValue
    abstract fun setTextFieldValue(value: TextFieldValue)
    abstract fun isStart(): Boolean
    abstract fun isEnd(): Boolean
    abstract fun focusFromLast()
    abstract fun focusFromStart()
    abstract fun focusTransform(transformPosition: Int)
    abstract fun focusX(x: Float, isStart: Boolean)
}


class ShortcutState{
    var isUpAvailable = false
    var isDownAvailable = false
    var isLeftAvailable = false
    var isRightAvailable = false
    var left = 0.0f
    override fun toString(): String {
        return "ShortcutState(isUpAvailable=$isUpAvailable, isDownAvailable=$isDownAvailable, isLeftAvailable=$isLeftAvailable, isRightAvailable=$isRightAvailable, left=$left)"
    }


}