package indi.midreamsheep.app.markdown.context.config;

import cn.hutool.json.JSONUtil;
import indi.midreamsheep.app.markdown.config.AbstractConfig;
import indi.midreamsheep.app.markdown.context.di.init.annotation.Init;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AbstractStandardConfig extends AbstractConfig {

    private static final String rootPath = System.getProperty("user.dir");


    @Init
    public void set(){
        init();
    }

    @Override
    public void write() {
        File file = new File(rootPath + "/configs/standard/" + geiFileName() + ".json");
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            new FileOutputStream(file).write(JSONUtil.toJsonStr(this).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getJsonString() {
        File file = new File(rootPath + "/configs/standard/" + geiFileName() + ".json");
        if (file.exists()) {
            try {
                byte[] bytes = new FileInputStream(file).readAllBytes();
                return new String(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "{}";
    }

    @Override
    public String getConfigName() {
        return this.getClass().getSimpleName();
    }

    protected String geiFileName(){
        return this.getClass().getSimpleName();
    }
}
