package indi.midreamsheep.app.tre.model.editor.operator.core;

import androidx.compose.ui.text.input.TextFieldValue;
import indi.midreamsheep.app.tre.model.editor.operator.TREOperatorAbstract;
import indi.midreamsheep.app.tre.shared.frame.engine.block.text.TRETextBlock;
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class TREContentChange extends TREOperatorAbstract {

    private TextFieldValue oldContent;
    private TextFieldValue newContent;
    private TRETextBlock treTextBlock;

    @Override
    public void execute(TREBlockManager stateManager) {
        treTextBlock.setTextFieldValue(newContent);
    }

    @Override
    public void undo(TREBlockManager stateManager) {
        treTextBlock.setTextFieldValue(oldContent);
    }
}
