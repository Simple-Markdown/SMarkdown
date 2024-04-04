package indi.midreamsheep.app.tre.model.editor.operator;

import indi.midreamsheep.app.tre.model.editor.manager.TREStateManager;
import lombok.Data;

@Data
public abstract class TREOperatorAbstract implements TREOperator{

    protected TREOperator nextOperator;
    protected TREOperator preOperator;
    protected int operatorIndex;

    @Override
    public abstract void execute(TREStateManager stateManager);

    @Override
    public abstract void undo(TREStateManager stateManager);


}
