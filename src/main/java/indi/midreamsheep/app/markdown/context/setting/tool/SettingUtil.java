package indi.midreamsheep.app.markdown.context.setting.tool;

import cn.hutool.json.JSONUtil;
import indi.midreamsheep.app.markdown.setting.AbstractConfig;
import indi.midreamsheep.app.markdown.setting.SettingItem;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SettingUtil {
    public static SettingItem<?>[] getConfigs(AbstractConfig config){
        List<SettingItem<?>> settingItems = new LinkedList<>();
        for (Field declaredField : config.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            try {
                Object o = declaredField.get(config);
                if (o instanceof SettingItem<?> item){
                    settingItems.add(item);
                }
            } catch (IllegalAccessException ignored) {}
        }
        return settingItems.toArray(new SettingItem<?>[0]);
    }

    public static String getJson(AbstractConfig config){
        Map<String,Object> map = new HashMap<>();
        for (Field declaredField : config.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            try {
                if (declaredField.get(config) instanceof SettingItem<?> item) {
                    map.put(declaredField.getName(), item.getData());
                }
            } catch (IllegalAccessException ignored) {}
        }
        return JSONUtil.toJsonStr(map);
    }

    public static void setData(AbstractConfig config,String json){
        Map<?,?> map = JSONUtil.toBean(json, Map.class);
        for (Field declaredField : config.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            try {
                if (declaredField.get(config) instanceof SettingItem<?> item) {
                    if (!map.containsKey(declaredField.getName())){
                        continue;
                    }
                    item.setData(map.get(declaredField.getName()));
                }
            } catch (IllegalAccessException ignored) {}
        }
    }
}
