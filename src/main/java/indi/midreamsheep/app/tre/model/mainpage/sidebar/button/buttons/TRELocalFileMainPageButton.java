package indi.midreamsheep.app.tre.model.mainpage.sidebar.button.buttons;

import androidx.compose.ui.graphics.ImageBitmap;
import indi.midreamsheep.app.tre.api.annotation.mainpage.MainPageButton;
import indi.midreamsheep.app.tre.context.app.TREAppContext;
import indi.midreamsheep.app.tre.context.app.viewmodel.pojo.TREWindow;
import indi.midreamsheep.app.tre.context.mainpage.TREMainPageContext;
import indi.midreamsheep.app.tre.model.mainpage.sidebar.button.TREMainPageButton;
import indi.midreamsheep.app.tre.model.setting.settings.StoreSetting;
import indi.midreamsheep.app.tre.tool.image.ImageToolKt;
import indi.midreamsheep.app.tre.ui.editor.OpenFileWindow;

import java.util.Objects;

@MainPageButton
public class TRELocalFileMainPageButton implements TREMainPageButton {

    @Override
    public String getButtonName() {
        return "编辑文档";
    }

    @Override
    public String getDescription() {
        return "打开一个本地文件";
    }

    @Override
    public ImageBitmap getIcon() {
        return ImageToolKt.loadImageBitmap(Objects.requireNonNull(StoreSetting.class.getResourceAsStream("/baseline_folder_black_18pt_3x.png")));
    }

    @Override
    public void onClick(TREMainPageContext context) {
        TREAppContext.Companion.getContext().getWindowAction().registerWindow(
                new TREWindow(
                        new OpenFileWindow(),
                        "文件编辑"
                )
        );
    }
}
