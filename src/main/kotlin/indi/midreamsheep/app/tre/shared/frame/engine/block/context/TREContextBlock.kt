package indi.midreamsheep.app.tre.shared.frame.engine.block.context

import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlockAbstract

abstract class TREContextBlock(context: TREEditorContext): TREBlockAbstract(context) {
    fun context() = getBlockManager().getContext()
}