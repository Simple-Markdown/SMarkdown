package indi.midreamsheep.app.tre.model.setting.setting;

import indi.midreamsheep.app.tre.api.Display;

public interface TRESettingItem<T> {
    void setData(Object data);
    T getData();
    Display getDisplay();
}
