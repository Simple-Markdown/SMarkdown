package indi.midreamsheep.app.tre.context.setting.settings;

import indi.midreamsheep.app.tre.context.api.annotation.setting.Config;
import indi.midreamsheep.app.tre.model.setting.AbstractStandardConfig;
import indi.midreamsheep.app.tre.model.setting.settingitems.IntInputSettingItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Config
public class HeadConfig extends AbstractStandardConfig {

    private IntInputSettingItem head1Size = new IntInputSettingItem(  20,"一级标题大小");
    private IntInputSettingItem head2Size = new IntInputSettingItem(  30,"二级标题大小");
    private IntInputSettingItem head3Size = new IntInputSettingItem(  40,"三级标题大小");
    private IntInputSettingItem head4Size = new IntInputSettingItem(  50,"四级标题大小");
    private IntInputSettingItem head5Size = new IntInputSettingItem(  60,"五级标题大小");
    private IntInputSettingItem head6Size = new IntInputSettingItem(  70,"六级标题大小");

    @NotNull
    @Override
    public String getConfigName() {
        return "标题配置";
    }

    public int getHeadSize(int level){
        return switch (level) {
            case 1 -> head1Size.getData();
            case 2 -> head2Size.getData();
            case 3 -> head3Size.getData();
            case 4 -> head4Size.getData();
            case 5 -> head5Size.getData();
            case 6 -> head6Size.getData();
            default -> 0;
        };
    }
}