package indi.midreamsheep.app.tre.model.editor.operator.core;

import indi.midreamsheep.app.tre.shared.render.manager.TREBlockManager;
import indi.midreamsheep.app.tre.model.editor.operator.TREOperator;
import indi.midreamsheep.app.tre.model.editor.operator.TREOperatorAbstract;

import java.util.LinkedList;
import java.util.List;

public class TREOperatorGroup extends TREOperatorAbstract {

    private final List<TREOperator> operators = new LinkedList<>();

    @Override
    public void execute(TREBlockManager stateManager) {
        for (TREOperator operator : operators) {
            operator.execute(stateManager);
        }
    }

    @Override
    public void undo(TREBlockManager stateManager) {
        //倒序执行
        for (int i = operators.size() - 1; i >= 0; i--) {
            operators.get(i).undo(stateManager);
        }
    }

    public void addOperator(TREOperator operator) {
        operators.add(operator);
    }

    @Override
    public String toString() {
        return "TREOperatorGroup{" +
                "operators=" + operators +
                '}';
    }
}
