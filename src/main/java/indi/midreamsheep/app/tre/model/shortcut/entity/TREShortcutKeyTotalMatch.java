package indi.midreamsheep.app.tre.model.shortcut.entity;

import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKeyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 快捷键的定义
 * 当且仅当所有的按键都被按下时，通过检查
 * */
@Data
@AllArgsConstructor
public class TREShortcutKeyTotalMatch implements TREShortcutKeyEntity {
    private Long[] keys;

    public TREShortcutKeyTotalMatch(long... keyCode) {
        this.keys = new Long[keyCode.length];
        for (int i = 0; i < keyCode.length; i++) {
            this.keys[i] = keyCode[i];
        }
    }

    @Override
    public boolean check(Set<Long> pressedKeys){
        return pressedKeys.containsAll(Set.of(keys))&&keys.length==pressedKeys.size();
    }
}
