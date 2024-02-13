package indi.midreamsheep.app.tre.model.setting.setting;

import indi.midreamsheep.app.tre.api.Display;

/**
 * 设置
 * 用于管理设置，通过ioc容器进行注入
 * 一个TRESetting内有多个TRESettingItem
 * */
public interface TRESetting {
    /**
     * 获取设置的显示方式
     * */
    Display getDisplay();
    /**
     * 获取设置的名称
     * */
    String getSettingName();
    /**
     * 保存设置
     * */
    boolean save();
}
