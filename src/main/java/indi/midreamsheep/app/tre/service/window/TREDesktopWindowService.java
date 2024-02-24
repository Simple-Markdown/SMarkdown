package indi.midreamsheep.app.tre.service.window;

import androidx.compose.runtime.snapshots.SnapshotStateList;
import kotlin.Unit;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Comment
@Slf4j
public class TREDesktopWindowService {
    private final SnapshotStateList<TREWindow> snapshotStateList = new SnapshotStateList<>();

    public void registerWindow(TREWindow window) {
        snapshotStateList.add(window);
    }

    public void removeWindow(TREWindow window) {
        snapshotStateList.remove(window);
    }

    public void removeWindow(String name) {
        snapshotStateList.remove(indexOf(name));
    }

    public void clear() {
        snapshotStateList.clear();
    }

    public void remove(TREWindow window) {
        snapshotStateList.remove(window);
    }

    public void remove(String name) {
        snapshotStateList.remove(indexOf(name));
    }

    public int indexOf(String name){
        for (int i = 0; i < snapshotStateList.size(); i++) {
            if (snapshotStateList.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public boolean containWindowByName(String name) {
        return indexOf(name) != -1;
    }
}
