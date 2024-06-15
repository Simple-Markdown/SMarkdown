package indi.midreamsheep.app.tre.service.window;

import androidx.compose.runtime.snapshots.SnapshotStateList;
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

    public void clear() {
        snapshotStateList.clear();
    }

}
