package indi.midreamsheep.app.tre.model.setting.settinggroup;

import androidx.compose.ui.graphics.ImageBitmap;
import indi.midreamsheep.app.tre.model.setting.setting.TRESetting;

import java.util.List;

public interface SettingGroup {
    String getName();
    ImageBitmap getIcon();
    List<TRESetting> getConfigs();
}