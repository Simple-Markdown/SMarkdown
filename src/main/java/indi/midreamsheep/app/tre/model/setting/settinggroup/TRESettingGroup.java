package indi.midreamsheep.app.tre.model.setting.settinggroup;

import androidx.compose.ui.graphics.ImageBitmap;
import indi.midreamsheep.app.tre.model.setting.setting.TRESetting;

import java.util.List;

/**
 * 设置组
 * 用于管理设置组，通过ioc容器进行注入
 * 其实现类必须使用 {@link indi.midreamsheep.app.tre.api.annotation.setting.SettingGroup} 进行标记
 * */
public interface TRESettingGroup {
    /**
     * 获取设置组的名称
     * */
    String getName();
    /**
     * 获取设置组的图标
     * */
    ImageBitmap getIcon();
    /**
     * 获取设置组的具体的设置子项
     * */
    List<TRESetting> getConfigs();
    /**
     * 保存当前组的所有设置
     * */
    void save();
}