package indi.midreamsheep.app.tre.model.setting.settings.store;

import androidx.compose.ui.graphics.ImageBitmap;
import indi.midreamsheep.app.tre.api.annotation.setting.SettingGroup;
import indi.midreamsheep.app.tre.model.setting.settinggroup.TRESettingGroup;
import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector;
import indi.midreamsheep.app.tre.model.setting.setting.TRESetting;
import indi.midreamsheep.app.tre.tool.image.ImageToolKt;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@SettingGroup
@Data
@Slf4j
public class StoreSettingGroup implements TRESettingGroup {

    @ListInjector(target = "StoreSetting")
    private List<TRESetting> configs = new LinkedList<>();
    @Override
    public String getName() {
        return "save setting";
    }

    @Override
    public ImageBitmap getIcon() {
        //获取资源目录下的图片
        return ImageToolKt.loadImageBitmap(Objects.requireNonNull(StoreSettingGroup.class.getResourceAsStream("/baseline_folder_black_18pt_3x.png")));
    }

    @Override
    public void save() {
        log.info("save the setting group {}", getName());
        for (TRESetting config : configs) {
            boolean write = config.save();
            if (!write) {
                log.error("setting item:{} save wrong", config.getSettingName());
            }else {
                log.info("setting item:{} save success", config.getSettingName());
            }
        }
    }

}
