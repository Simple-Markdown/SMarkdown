package indi.midreamsheep.app.markdown.context.setting.settings;

import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector;
import indi.midreamsheep.app.markdown.model.setting.AbstractStandardConfig;
import indi.midreamsheep.app.markdown.model.setting.settingitems.StringSettingItem;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Comment
@Data
@ListInjector
public class ThemeColorConfig extends AbstractStandardConfig {
    private StringSettingItem primaryColor = new StringSettingItem("#409EFF","主色");
}
