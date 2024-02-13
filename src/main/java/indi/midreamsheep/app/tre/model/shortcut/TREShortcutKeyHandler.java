package indi.midreamsheep.app.tre.model.shortcut;

import indi.midreamsheep.app.tre.context.TREContext;
import indi.midreamsheep.app.tre.model.shortcut.entity.TREShortcutKeyTotalMatch;

import java.util.List;

/**
 * 快建方法的接口，用于定义一个快建方法的处理器
 * */
public interface TREShortcutKeyHandler {
    /**
     * 执行快捷键的操作
     * */
    void action(TREContext context);
    /**
     * 判断当前上下文环境是否可用
     * */
    boolean isEnable(TREContext context);
    /**
     * 获取快捷键的定义
     * */
    List<TREShortcutKeyTotalMatch> getKeys();
}
