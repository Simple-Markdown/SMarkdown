package indi.midreamsheep.app.tre.model.mainpage.file;

import androidx.compose.runtime.MutableState;
import indi.midreamsheep.app.tre.api.Recall;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TREFile {
    List<TREFile> listSubFiles();
    boolean isDirectory();
    String getName();
    String readText();
    Recall<TREFile> getRecall();

    void writeText(@NotNull String sourceContent);
}
