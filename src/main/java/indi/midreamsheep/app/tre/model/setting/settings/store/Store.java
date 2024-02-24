package indi.midreamsheep.app.tre.model.setting.settings.store;

import indi.midreamsheep.app.tre.api.annotation.setting.StoreSetting;
import indi.midreamsheep.app.tre.constant.AppPathConstant;
import indi.midreamsheep.app.tre.model.setting.setting.TRECoreSetting;
import indi.midreamsheep.app.tre.model.setting.settingitems.StringSettingItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 存储的配置
 * */
@EqualsAndHashCode(callSuper = true)
@Data
@StoreSetting
public class Store extends TRECoreSetting {
    private StringSettingItem rootPath = new StringSettingItem(AppPathConstant.ROOT_PATH,"根目录");
}