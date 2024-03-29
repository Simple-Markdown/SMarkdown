package indi.midreamsheep.app.tre.model.mainpage.sidebar.button.buttons;

import androidx.compose.ui.graphics.ImageBitmap;
import indi.midreamsheep.app.tre.api.annotation.mainpage.MainPageButton;
import indi.midreamsheep.app.tre.context.mainpage.TREMainPageContext;
import indi.midreamsheep.app.tre.model.mainpage.sidebar.button.TREMainPageButton;
import indi.midreamsheep.app.tre.model.setting.settings.store.StoreSettingGroup;
import indi.midreamsheep.app.tre.service.image.TREImageTool;
import indi.midreamsheep.app.tre.ui.page.editorpage.OpenFileWindow;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Injector;

import java.util.Objects;

@MainPageButton
public class TRELocalFileMainPageButton implements TREMainPageButton {

    @Injector
    TREImageTool imageTool;
    
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
        return imageTool.readImage(Objects.requireNonNull(StoreSettingGroup.class.getResourceAsStream("/baseline_folder_black_18pt_3x.png")));
    }

    @Override
    public void onClick(TREMainPageContext context) {
        new OpenFileWindow().register();
    }
}
