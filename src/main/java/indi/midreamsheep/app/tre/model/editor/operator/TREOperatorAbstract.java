package indi.midreamsheep.app.tre.model.editor.operator;

import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager;
import lombok.Data;

@Data
public abstract class TREOperatorAbstract implements TREOperator{

    protected TREOperator nextOperator;
    protected TREOperator preOperator;
    protected int operatorIndex;

    @Override
    public abstract void execute(TREBlockManager stateManager);

    @Override
    public abstract void undo(TREBlockManager stateManager);


}
