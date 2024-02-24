package indi.midreamsheep.app.tre.constant;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static java.io.File.separator;

public class AppPathConstant {
    //项目根路径
    public static final String CORE_JAR_PATH = URLDecoder.decode(
            AppPathConstant.class.getProtectionDomain().getCodeSource().getLocation().getPath(), StandardCharsets.UTF_8
    );

    public static final String ROOT_PATH = CORE_JAR_PATH.substring(0, CORE_JAR_PATH.lastIndexOf("/"));

    //配置文件路径
    public static final String ROOT_CONFIG_PATH = ROOT_PATH + separator + "configs";
    public static final String LANGUAGE_PATH = ROOT_CONFIG_PATH + separator + "language" + separator + "language.properties";

    //插件路径
    public static final String PLUGIN_PATH = ROOT_PATH + separator + "plugins";
    public static final String PLUGIN_CONFIG_PATH = PLUGIN_PATH + separator + "plugin-config.json";

    //libs路径
    public static final String LIBS_PATH = ROOT_PATH + separator + "libs";

    //缓存路径
    public static final String CACHE_PATH = ROOT_PATH + separator + "cache";
    //扫描缓存
    public static final String CACHE_SCANNER_PATH = CACHE_PATH + separator + "scanner.catch";
}
