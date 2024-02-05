package indi.midreamsheep.app.tre.model.mainpage.sidebar.button.buttons;

import indi.midreamsheep.app.tre.api.Display;
import indi.midreamsheep.app.tre.context.api.annotation.mainpage.MainPageButton;
import indi.midreamsheep.app.tre.context.mainpage.TREMainPageContext;
import indi.midreamsheep.app.tre.model.mainpage.sidebar.button.TREMainPageButton;
import indi.midreamsheep.app.tre.ui.mainpage.sidebar.button.buttons.ButtonDisplay;

@MainPageButton
public class TRELocalFileMainPageButton implements TREMainPageButton {
    @Override
    public Display getDisplay(TREMainPageContext context) {
        return new ButtonDisplay(context);
    }
}
