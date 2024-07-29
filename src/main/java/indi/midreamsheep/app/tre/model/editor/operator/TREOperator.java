package indi.midreamsheep.app.tre.model.editor.operator;

import indi.midreamsheep.app.tre.shared.frame.manager.TREBlockManager;

/**
 * 操作接口
 * */
public interface TREOperator {
    /**
     * 执行操作
     * @param stateManager 状态管理器
     * */
    void execute(TREBlockManager stateManager);
    /**
     * 撤销操作
     * @param stateManager 状态管理器
     * */
    void undo(TREBlockManager stateManager);
    /**
     * 设置下一个操作
     * @param nextOperator 下一个操作
     * */
    void setNextOperator(TREOperator nextOperator);
    /**
     * 获取下一个操作
     * @return 下一个操作
     * */
    TREOperator getNextOperator();
    /**
     * 设置上一个操作
     * @param preOperator 上一个操作
     * */
    void setPreOperator(TREOperator preOperator);
    /**
     * 获取上一个操作
     * @return 上一个操作
     * */
    TREOperator getPreOperator();
    /**
     * 获取操作索引
     * @return 操作索引
     * */
    int getOperatorIndex();
    /**
     * 设置操作索引
     * @param index 操作索引
     * */
    void setOperatorIndex(int index);
}