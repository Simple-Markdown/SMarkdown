package indi.midreamsheep.app.tre.model.editor.operator.core;

import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.block.TREBlock;
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREBlockManager;
import indi.midreamsheep.app.tre.model.editor.operator.TREOperatorAbstract;

public class TREBlockDelete extends TREOperatorAbstract {

    private final int lineIndex;
    private TREBlock block;

    public TREBlockDelete(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    @Override
    public void execute(TREBlockManager stateManager) {
        block = stateManager.getTREBlock(lineIndex);
        stateManager.removeBlock(lineIndex);
    }

    @Override
    public void undo(TREBlockManager stateManager) {
        stateManager.addBlock(lineIndex, block);
    }
}