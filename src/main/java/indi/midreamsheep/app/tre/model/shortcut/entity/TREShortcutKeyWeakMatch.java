package indi.midreamsheep.app.tre.model.shortcut.entity;

import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKeyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 快捷键的定义
 * 当指定建被按下时，通过检查
 * */
@Data
@AllArgsConstructor
public class TREShortcutKeyWeakMatch implements TREShortcutKeyEntity {
    private Long[] keys;

    public TREShortcutKeyWeakMatch(long... keyCode) {
        this.keys = new Long[keyCode.length];
        for (int i = 0; i < keyCode.length; i++) {
            this.keys[i] = keyCode[i];
        }
    }

    @Override
    public boolean check(Set<Long> pressedKeys) {
        return pressedKeys.containsAll(Set.of(keys));
    }
}
