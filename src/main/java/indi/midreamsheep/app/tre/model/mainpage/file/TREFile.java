package indi.midreamsheep.app.tre.model.mainpage.file;

import org.jetbrains.annotations.NotNull;

/**
 * TRE使用的抽象文件接口
 * @author midreamsheep
 * */
public interface TREFile {
    /**
     * 获取文件名
     * @return 文件名
     * */
    String getName();
    /**
     * 读取文件内容
     * 可以通过网络或者本地文件系统读取
     * @return 文件内容
     * */
    String readText();
    /**
     * 写出文件
     * 可以通过网络或者本地文件系统写出
     * */
    void writeText(@NotNull String sourceContent);
}
