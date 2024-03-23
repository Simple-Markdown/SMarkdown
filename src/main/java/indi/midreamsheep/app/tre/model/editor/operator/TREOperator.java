package indi.midreamsheep.app.tre.model.editor.operator;

import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager;

/**
 * 操作接口
 * */
public interface TREOperator {
    void execute(TREStateManager stateManager);
    void undo(TREStateManager stateManager);
    boolean check(TREOperator newOperator);
}