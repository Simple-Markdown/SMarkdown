package indi.midreamsheep.app.tre.model.setting.settings;

import androidx.compose.ui.graphics.ImageBitmap;
import indi.midreamsheep.app.tre.api.tool.image.ImageToolKt;
import indi.midreamsheep.app.tre.context.api.annotation.setting.ASettingGroup;
import indi.midreamsheep.app.tre.context.di.inject.listdi.annotation.ListInjector;
import indi.midreamsheep.app.tre.model.setting.setting.TRESetting;
import indi.midreamsheep.app.tre.model.setting.setting.TRESettingItem;
import indi.midreamsheep.app.tre.model.setting.settinggroup.SettingGroup;
import lombok.Data;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

@ASettingGroup
@Data
public class StoreSetting implements SettingGroup {

    @ListInjector(target = "config")
    private List<TRESetting> configs = new LinkedList<>();
    @Override
    public String getName() {
        return "保存设置";
    }

    @Override
    public ImageBitmap getIcon() {
        return ImageToolKt.loadImageBitmap( new File("/home/midreamsheep/workplace/markdown/src/main/resources/baseline_folder_black_18pt_3x.png"));
    }

}