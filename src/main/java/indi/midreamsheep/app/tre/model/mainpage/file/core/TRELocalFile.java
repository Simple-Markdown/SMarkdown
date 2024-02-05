package indi.midreamsheep.app.tre.model.mainpage.file.core;

import androidx.compose.runtime.MutableState;
import indi.midreamsheep.app.tre.api.Recall;
import indi.midreamsheep.app.tre.model.mainpage.file.TREFile;
import indi.midreamsheep.app.tre.ui.mainpage.file.FileOpenRecall;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class TRELocalFile implements TREFile {

    private File file;

    @Override
    public List<TREFile> listSubFiles() {
        File[] files = file.listFiles();
        List<TREFile> treFiles = new LinkedList<>();
        assert files != null;
        for (File subFile : files) {
            treFiles.add(new TRELocalFile(subFile));
        }
        return treFiles;
    }

    @Override
    public boolean isDirectory() {
        return file.isDirectory();
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public Recall<MutableState<Boolean>> getRecall() {
        return new FileOpenRecall(file);
    }
}
