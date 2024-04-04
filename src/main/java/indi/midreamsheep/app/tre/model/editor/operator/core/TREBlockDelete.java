package indi.midreamsheep.app.tre.model.editor.operator.core;

import indi.midreamsheep.app.tre.model.editor.block.TREBlock;
import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager;
import indi.midreamsheep.app.tre.model.editor.operator.TREOperatorAbstract;

public class TREBlockDelete extends TREOperatorAbstract {

    private final int lineIndex;
    private TREBlock block;

    public TREBlockDelete(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    @Override
    public void execute(TREStateManager stateManager) {
        block = stateManager.getTREBlockStateList().get(lineIndex).getLine();
        stateManager.getTREBlockStateList().remove(lineIndex);
    }

    @Override
    public void undo(TREStateManager stateManager) {
        stateManager.getTREBlockStateList().add(lineIndex, block.getLineState());
    }
}