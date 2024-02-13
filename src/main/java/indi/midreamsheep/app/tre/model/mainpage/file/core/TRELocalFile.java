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

/**
 * 默认的本地文件实现
 * 通过本地文件系统读取文件内容
 * @see TREFile
 * @author midreamsheep
 * */
@Data
@AllArgsConstructor
public class TRELocalFile implements TREFile {

    private File file;

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String readText() {
        return FileUtil.readString(file, Charset.defaultCharset());
    }

    @Override
    public void writeText(@NotNull String sourceContent) {
        FileUtil.writeString(sourceContent, file, Charset.defaultCharset());
    }
}
