package indi.midreamsheep.app.tre.model.editor.operator.core;

import indi.midreamsheep.app.tre.model.editor.block.TREBlock;
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager;
import indi.midreamsheep.app.tre.model.editor.operator.TREOperatorAbstract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class TREBlockInsert extends TREOperatorAbstract {

    private int lineIndex;
    private TREBlock block;

    @Override
    public void execute(TREStateManager stateManager) {
        stateManager.getTREBlockStateList().add(lineIndex, block.getLineState());
    }

    @Override
    public void undo(TREStateManager stateManager) {
        stateManager.getTREBlockStateList().remove(lineIndex);
    }
}
