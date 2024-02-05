package indi.midreamsheep.app.tre.model.shortcut;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class TREShortcutKey {
    private Long[] keys;

    public TREShortcutKey(long... keyCode) {
        this.keys = new Long[keyCode.length];
        for (int i = 0; i < keyCode.length; i++) {
            this.keys[i] = keyCode[i];
        }
    }

    public boolean check(Set<Long> pressedKeys){
        return pressedKeys.containsAll(Set.of(keys))&&keys.length==pressedKeys.size();
    }
}
