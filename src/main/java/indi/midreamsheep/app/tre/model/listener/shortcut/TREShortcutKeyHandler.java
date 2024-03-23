package indi.midreamsheep.app.tre.model.listener.shortcut;

import indi.midreamsheep.app.tre.api.inter.event.TREEvent;
import indi.midreamsheep.app.tre.context.TREContext;

import java.util.List;

/**
 * 快建方法的接口，用于定义一个快建方法的处理器
 * */
public interface TREShortcutKeyHandler extends TREEvent {
    /**
     * 判断当前上下文环境是否可用
     * */
    boolean isEnable(TREContext context);
    /**
     * 获取快捷键的定义
     * */
    List<TREShortcutKeyChecker> getKeys();
}
