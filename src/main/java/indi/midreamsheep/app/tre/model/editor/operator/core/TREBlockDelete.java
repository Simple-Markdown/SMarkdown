package indi.midreamsheep.app.tre.model.editor.operator.core;

import indi.midreamsheep.app.tre.model.editor.block.TREBlock;
import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager;
import indi.midreamsheep.app.tre.model.editor.operator.TREOperatorAbstract;

public class TREBlockDelete extends TREOperatorAbstract {

    private final int lineIndex;
    private TREBlock block;

    public TREBlockDelete(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    @Override
    public void execute(TREBlockManager stateManager) {
        block = stateManager.getTREBlockStateList().get(lineIndex).getBlock();
        stateManager.getTREBlockStateList().remove(lineIndex);
    }

    @Override
    public void undo(TREBlockManager stateManager) {
        stateManager.getTREBlockStateList().add(lineIndex, block.getLineState());
    }
}