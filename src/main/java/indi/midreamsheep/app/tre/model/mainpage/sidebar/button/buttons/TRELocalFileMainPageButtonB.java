package indi.midreamsheep.app.tre.model.mainpage.sidebar.button.buttons;

import androidx.compose.ui.graphics.ImageBitmap;
import indi.midreamsheep.app.tre.api.tool.image.ImageToolKt;
import indi.midreamsheep.app.tre.context.api.annotation.mainpage.MainPageButton;
import indi.midreamsheep.app.tre.context.mainpage.TREMainPageContext;
import indi.midreamsheep.app.tre.model.mainpage.sidebar.button.TREMainPageButton;

import java.io.File;

@MainPageButton
public class TRELocalFileMainPageButtonB implements TREMainPageButton {

    @Override
    public String getButtonName() {
        return "新建文档";
    }

    @Override
    public String getDescription() {
        return "新建一个本地文件";
    }

    @Override
    public ImageBitmap getIcon() {
        return ImageToolKt.loadImageBitmap( new File("/home/midreamsheep/workplace/markdown/src/main/resources/baseline_folder_black_18pt_3x.png"));
    }

    @Override
    public void onClick(TREMainPageContext context) {

    }
}