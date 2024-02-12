package indi.midreamsheep.app.tre.model.setting.settings;

import androidx.compose.ui.graphics.ImageBitmap;
import indi.midreamsheep.app.tre.api.annotation.setting.SettingGroup;
import indi.midreamsheep.app.tre.model.setting.settinggroup.TRESettingGroup;
import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector;
import indi.midreamsheep.app.tre.model.setting.setting.TRESetting;
import indi.midreamsheep.app.tre.tool.image.ImageToolKt;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@SettingGroup
@Data
public class StoreSetting implements TRESettingGroup {

    @ListInjector(target = "config")
    private List<TRESetting> configs = new LinkedList<>();
    @Override
    public String getName() {
        return "保存设置";
    }

    @Override
    public ImageBitmap getIcon() {
        //获取资源目录下的图片
        return ImageToolKt.loadImageBitmap(Objects.requireNonNull(StoreSetting.class.getResourceAsStream("/baseline_folder_black_18pt_3x.png")));
    }

}
