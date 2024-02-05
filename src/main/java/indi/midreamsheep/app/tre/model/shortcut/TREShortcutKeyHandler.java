package indi.midreamsheep.app.tre.model.shortcut;

import indi.midreamsheep.app.tre.context.TREContext;

import java.util.List;

public interface TREShortcutKeyHandler {
    void action(TREContext context);
    boolean isEnable(TREContext context);
    List<TREShortcutKey> getKeys();
}
