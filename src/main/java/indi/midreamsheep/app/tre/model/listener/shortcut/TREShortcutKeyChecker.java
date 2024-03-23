package indi.midreamsheep.app.tre.model.listener.shortcut;

import java.util.Set;

public interface TREShortcutKeyChecker {
    boolean check(Set<Long> pressedKeys);
}
