package indi.midreamsheep.app.tre.model.mainpage.file.core;

import cn.hutool.core.io.FileUtil;
import indi.midreamsheep.app.tre.api.Recall;
import indi.midreamsheep.app.tre.model.mainpage.file.TREFile;
import indi.midreamsheep.app.tre.ui.filechooser.FileOpenRecall;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.File;
import java.nio.charset.Charset;
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
    public String readText() {
        return FileUtil.readString(file, Charset.defaultCharset());
    }

    @Override
    public Recall<TREFile> getRecall() {
        return new FileOpenRecall();
    }

    @Override
    public void writeText(@NotNull String sourceContent) {
        FileUtil.writeString(sourceContent, file, Charset.defaultCharset());
    }
}
