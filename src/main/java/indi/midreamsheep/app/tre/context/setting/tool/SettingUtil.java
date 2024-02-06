package indi.midreamsheep.app.tre.context.setting.tool;

import cn.hutool.json.JSONUtil;
import indi.midreamsheep.app.tre.model.setting.setting.TRESetting;
import indi.midreamsheep.app.tre.model.setting.setting.TRESettingItem;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 有关设置的工具类
 * */
public class SettingUtil {
    /**
     * 通过反射获取配置类中的配置项
     * @param config 配置类
     *               该类必须继承 {@link TRESetting}
     *               且配置项必须是 {@link TRESettingItem} 的子类
     * @return 配置项数组
     * */
    public static TRESettingItem<?>[] getConfigs(TRESetting config){
        List<TRESettingItem<?>> settingItems = new LinkedList<>();
        for (Field declaredField : config.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            try {
                Object o = declaredField.get(config);
                if (o instanceof TRESettingItem<?> item){
                    settingItems.add(item);
                }
            } catch (IllegalAccessException ignored) {}
        }
        return settingItems.toArray(new TRESettingItem<?>[0]);
    }

    /**
     * 通过反射获取配置类本地存储的json
     * */
    public static String getJson(TRESetting config){
        Map<String,Object> map = new HashMap<>();
        for (Field declaredField : config.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            try {
                if (declaredField.get(config) instanceof TRESettingItem<?> item) {
                    map.put(declaredField.getName(), item.getData());
                }
            } catch (IllegalAccessException ignored) {}
        }
        return JSONUtil.toJsonStr(map);
    }

    /**
     * 通过反射设置配置类的数据
     * */
    public static void setData(TRESetting config, String json){
        Map<?,?> map = JSONUtil.toBean(json, Map.class);
        for (Field declaredField : config.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            try {
                if (declaredField.get(config) instanceof TRESettingItem<?> item) {
                    if (!map.containsKey(declaredField.getName())){
                        continue;
                    }
                    item.setData(map.get(declaredField.getName()));
                }
            } catch (IllegalAccessException ignored) {}
        }
    }
}
