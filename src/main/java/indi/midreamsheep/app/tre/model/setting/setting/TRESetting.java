package indi.midreamsheep.app.tre.model.setting.setting;

import indi.midreamsheep.app.tre.api.Display;

public interface TRESetting {
    Display getDisplay();
    String getSettingName();
    boolean write();
}
