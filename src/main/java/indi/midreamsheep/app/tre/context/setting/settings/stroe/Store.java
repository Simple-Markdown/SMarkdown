package indi.midreamsheep.app.tre.context.setting.settings.stroe;

import indi.midreamsheep.app.tre.context.api.annotation.setting.Config;
import indi.midreamsheep.app.tre.model.setting.setting.TRECoreSetting;
import indi.midreamsheep.app.tre.model.setting.settingitems.StringSettingItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 存储的配置
 * */
@EqualsAndHashCode(callSuper = true)
@Data
@Config
public class Store extends TRECoreSetting {
    private StringSettingItem rootPath = new StringSettingItem(System.getProperty("user.dir")/*+ File.separator + "files" + File.separator*/,"根目录");
}