package indi.midreamsheep.app.tre.model.mainpage.sidebar.button;

import indi.midreamsheep.app.tre.api.Display;
import indi.midreamsheep.app.tre.context.mainpage.TREMainPageContext;

public interface TREMainPageButton {
    Display getDisplay(TREMainPageContext context);
}