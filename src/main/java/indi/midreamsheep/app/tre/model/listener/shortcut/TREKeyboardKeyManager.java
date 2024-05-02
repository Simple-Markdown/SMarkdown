package indi.midreamsheep.app.tre.model.listener.shortcut;

import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Comment
@Data
@Slf4j
public class TREKeyboardKeyManager {
    private final Set<Long> pressedKeys = new HashSet<>();
    public void addPressedKey(long keyCode) {
        pressedKeys.add(keyCode);
    }
    public void removePressedKey(long keyCode) {
        pressedKeys.remove(keyCode);
    }
    public void clearPressedKeys() {
        pressedKeys.clear();
    }
    public boolean isPressed(long keyCode) {
        return pressedKeys.contains(keyCode);
    }
    public boolean isPressed(Set<Long> keyCodes) {
        return pressedKeys.containsAll(keyCodes);
    }
}
