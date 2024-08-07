package indi.midreamsheep.app.tre.model.editor.operator.core;

import indi.midreamsheep.app.tre.shared.frame.engine.block.TREBlock;
import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager;
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
    public void execute(TREBlockManager stateManager) {
        stateManager.addBlock(lineIndex, block);
    }

    @Override
    public void undo(TREBlockManager stateManager) {
        stateManager.removeBlock(lineIndex);
    }
}
