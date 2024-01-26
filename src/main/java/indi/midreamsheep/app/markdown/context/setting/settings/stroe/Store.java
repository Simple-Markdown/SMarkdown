package indi.midreamsheep.app.markdown.context.setting.settings.stroe;

import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector;
import indi.midreamsheep.app.markdown.setting.AbstractStandardConfig;
import indi.midreamsheep.app.markdown.setting.settingitems.StringSettingItem;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;

@EqualsAndHashCode(callSuper = true)
@Comment
@Data
@ListInjector
public class Store extends AbstractStandardConfig {
    private StringSettingItem rootPath = new StringSettingItem(System.getProperty("user.dir")/*+ File.separator + "files" + File.separator*/,"根目录");
}