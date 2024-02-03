package indi.midreamsheep.app.markdown.context.di.scan;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class PluginScannerTool {
    public static PluginConfig getPluginLoading() {
        String root = System.getProperty("user.dir");
        //读取plugins下的配置文件
        File file = new File(root + "/plugins/plugin-config.json");
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
