package indi.midreamsheep.app.tre.model.setting.setting;

import androidx.compose.runtime.Composable;
import cn.hutool.core.io.FileUtil;
import indi.midreamsheep.app.tre.api.Display;
import indi.midreamsheep.app.tre.constant.ProjectPathConstant;
import indi.midreamsheep.app.tre.service.ioc.di.init.annotation.Init;
import indi.midreamsheep.app.tre.model.setting.setting.tool.SettingUtil;
import indi.midreamsheep.app.tre.model.setting.SettingDisplay;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 核心设置的模板类
 * 用于管理设置，通过ioc容器进行注入
 * */
@Slf4j
public class TRECoreSetting implements TRESetting{

    //配置文件的根目录
    private static final String rootPath = ProjectPathConstant.ROOT_PATH+ File.separator + "configs" + File.separator;

    /**
     * 初始化读取本地配置文件并通过反射对子项进行赋值
     * */
    @Init
    public void init(){
        File file = new File(rootPath+ getSettingPath());
        if (!file.exists()){
            return;
        }
        String s = FileUtil.readString(file, Charset.defaultCharset());
        SettingUtil.setData(this,s);
    }

    /**
     * 使用默认的显示方式
     * */
    @Override
    public Display getDisplay() {
        return new SettingDisplay(SettingUtil.getConfigs(this));
    }

    @Override
     public String getSettingName() {
        return this.getClass().getSimpleName();
    }

    @Composable
    public boolean save() {
        //写入文件
        File file = new File(rootPath+ getSettingName()+".json");
        if (!file.exists()){
            try {
                if (!file.createNewFile()) {
                    log.error("create file wrong path:{}",file.getAbsolutePath());
                }
            } catch (IOException e) {
                log.error("write config wrong, target path : {} \n\t wrong message:{}",file.getAbsolutePath(),e.getMessage());
            }
        }
        FileUtil.writeString(getJsonString(),file,Charset.defaultCharset());
        return true;
    }

    private String getJsonString(){
        return SettingUtil.getJson(this);
    }

    private String getSettingPath(){
        return getSettingName()+".json";
    }
}
