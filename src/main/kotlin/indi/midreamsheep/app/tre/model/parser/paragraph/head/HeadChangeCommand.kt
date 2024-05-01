package indi.midreamsheep.app.tre.model.parser.paragraph.head

import androidx.compose.ui.text.TextRange
import indi.midreamsheep.app.tre.model.editor.block.TRECoreBlock
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager
import indi.midreamsheep.app.tre.model.editor.operator.TREOperatorAbstract

private fun String.insertString(
    insert: String,
    index: Int
): String {
    return this.substring(0, index) + insert + this.substring(index)
}

class HeadChangeCommand(
    private val oldLevel: Int,
    private val newLevel: Int,
    private val styleText: StyleTextHeadRoot,
    private val lineNumber: Int,
): TREOperatorAbstract() {
    override fun execute(stateManager: TREStateManager) {
        changeLevel(oldLevel, newLevel, stateManager)
    }

    override fun undo(stateManager: TREStateManager) {
        changeLevel(newLevel, oldLevel, stateManager)
    }

    private fun changeLevel(
        fromLevel: Int,
        toLevel: Int,
        stateManager: TREStateManager
    ){
        val isDelete = toLevel==0
        val treCoreBlock = stateManager.getTREBlockStateList()[lineNumber].line as TRECoreBlock
        val textFieldValue = treCoreBlock.getTextFieldValue()
        val originalStartOffset = styleText.getOriginalStartOffset()
        val newValue = textFieldValue.copy(
            text = textFieldValue.text.removeRange(originalStartOffset,originalStartOffset+fromLevel + if(isDelete) 1 else 0).insertString("#".repeat(toLevel),originalStartOffset),
            selection = TextRange(
                start = textFieldValue.selection.start+ toLevel - fromLevel - if(isDelete) 1 else 0,
                end = textFieldValue.selection.end+ toLevel - fromLevel  - if(isDelete) 1 else 0
            )
        )
        treCoreBlock.setTextFieldValue(newValue)
    }
}