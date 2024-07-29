package indi.midreamsheep.app.tre.shared.frame.engine.block.text

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