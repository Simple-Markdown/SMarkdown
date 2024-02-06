package indi.midreamsheep.app.tre.model.mainpage.sidebar.button;

import androidx.compose.ui.graphics.ImageBitmap;
import indi.midreamsheep.app.tre.api.Display;
import indi.midreamsheep.app.tre.context.mainpage.TREMainPageContext;

public interface TREMainPageButton {
    String getButtonName();
    String getDescription();
    ImageBitmap getIcon();
    void onClick(TREMainPageContext context);
}