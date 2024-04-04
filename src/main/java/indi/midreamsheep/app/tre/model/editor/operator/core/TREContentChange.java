package indi.midreamsheep.app.tre.model.editor.operator.core;

import androidx.compose.ui.text.input.TextFieldValue;
import indi.midreamsheep.app.tre.model.editor.block.TREBlock;
import indi.midreamsheep.app.tre.model.editor.block.TRETextBlock;
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager;
import indi.midreamsheep.app.tre.model.editor.operator.TREOperatorAbstract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class TREContentChange extends TREOperatorAbstract {

    private TextFieldValue oldContent;
    private TextFieldValue newContent;
    private int lineIndex;

    @Override
    public void execute(TREStateManager stateManager) {
        TREBlock line = stateManager.getTREBlockStateList().get(lineIndex).getLine();
        if (line instanceof TRETextBlock textBlock) {
            textBlock.setTextFieldValue(newContent);
        }
    }

    @Override
    public void undo(TREStateManager stateManager) {
        TREBlock line = stateManager.getTREBlockStateList().get(lineIndex).getLine();
        if (line instanceof TRETextBlock textBlock) {
            textBlock.setTextFieldValue(oldContent);
        }
    }
}
