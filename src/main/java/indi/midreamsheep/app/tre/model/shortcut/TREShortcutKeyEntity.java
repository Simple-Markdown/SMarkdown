package indi.midreamsheep.app.tre.model.shortcut;

import java.util.Set;

public interface TREShortcutKeyEntity {
    boolean check(Set<Long> pressedKeys);
}
