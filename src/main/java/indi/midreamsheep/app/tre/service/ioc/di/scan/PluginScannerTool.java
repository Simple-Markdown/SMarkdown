package indi.midreamsheep.app.tre.service.ioc.di.scan;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import indi.midreamsheep.app.tre.constant.AppPathConstant;
import lombok.Data;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 插件扫描的工具，用于读取插件的配置文件并解析需要注入的插件
 * */
public class PluginScannerTool {
    public static PluginConfig getPluginLoading() {
        //读取plugins下的配置文件
        File file = new File(AppPathConstant.PLUGIN_CONFIG_PATH);
        if (!(file.exists()&&file.isFile())){
            return new PluginConfig();
        }
        //读取file文件内的文本数据用hutool
        String json = FileUtil.readUtf8String(file);
        //解析json
        return JSONUtil.toBean(json, PluginConfig.class);
    }

    @Data
    public static class PluginConfig {
        private List<String> name = new LinkedList<>();
    }
}
