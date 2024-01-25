package indi.midreamsheep.app.markdown.context.setting.settings;

import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector;
import indi.midreamsheep.app.markdown.setting.AbstractStandardConfig;
import indi.midreamsheep.app.markdown.setting.settingitems.IntInputSettingItem;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Comment
@Data
@ListInjector
public class HeadConfig extends AbstractStandardConfig {
    private IntInputSettingItem head1Size = new IntInputSettingItem(  20,"一级标题大小");
    private IntInputSettingItem head2Size = new IntInputSettingItem(  30,"二级标题大小");
    private IntInputSettingItem head3Size = new IntInputSettingItem(  40,"三级标题大小");
    private IntInputSettingItem head4Size = new IntInputSettingItem(  50,"四级标题大小");
    private IntInputSettingItem head5Size = new IntInputSettingItem(  60,"五级标题大小");
    private IntInputSettingItem head6Size = new IntInputSettingItem(  70,"六级标题大小");
}