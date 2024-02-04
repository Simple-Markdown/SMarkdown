package indi.midreamsheep.app.tre.context.setting.settings;

import indi.midreamsheep.app.tre.context.api.annotation.setting.Config;
import indi.midreamsheep.app.tre.model.setting.AbstractStandardConfig;
import indi.midreamsheep.app.tre.model.setting.settingitems.StringSettingItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 主题颜色配置
 * */
@EqualsAndHashCode(callSuper = true)
@Data
@Config
public class ThemeColorConfig extends AbstractStandardConfig {
    private StringSettingItem primaryColor = new StringSettingItem("#409EFF","主色");
}
