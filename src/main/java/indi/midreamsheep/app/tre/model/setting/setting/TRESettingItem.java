package indi.midreamsheep.app.tre.model.setting.setting;

import indi.midreamsheep.app.tre.shared.api.display.Display;

/**
 * 设置项
 * 用于管理设置项，通过ioc容器进行注入
 * 其实现类用自定义注解诶进行标记
 * @param <T> 设置项的数据类型
 * */
public interface TRESettingItem<T> {
    /**
     * 设置具体的数据内容
     * */
    void setData(Object data);
    /**
     * 获取具体的数据内容
     * */
    T getData();
    /**
     * 获取设置项的显示方式
     * */
    Display getDisplay();
    /**
     * 获取设置项的名称，主要用于保存文件时的标记
     * */
    String getName();
    /**
     * 设置设置项的名称
     * */
    void setName(String name);
}
