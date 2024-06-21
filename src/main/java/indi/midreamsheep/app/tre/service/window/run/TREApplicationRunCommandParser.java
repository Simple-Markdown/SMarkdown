package indi.midreamsheep.app.tre.service.window.run;

import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext;
import indi.midreamsheep.app.tre.desktop.page.home.TREHomePageWindowContext;
import indi.midreamsheep.app.tre.model.mainpage.file.core.TRELocalFile;
import indi.midreamsheep.app.tre.service.window.TREWindow;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 用于解析启动的命令
 * */
@Comment
@Slf4j
public class TREApplicationRunCommandParser {

    public void parse(String[] args) {
        if (args.length == 0) {
            //注册主窗口
            new TREWindow(new TREHomePageWindowContext()).register();
        }
    }

    private void parse(String arg){
        if (arg.startsWith("command:")){
            //解析命令
            parseCommand(arg.replace("command:",""));
        }else{
            //打开文件
            openFile(arg);
        }
    }

    private void parseCommand(String command){
        //TODO
    }

    private void openFile(String filePath){
        File file = new File(filePath);
        if (!file.exists()||file.isDirectory()){
            log.error("file not found or is a directory:{}",filePath);
            return;
        }
        new TREWindow(new TREEditorWindowContext(new TRELocalFile(file))).register();
    }
}
