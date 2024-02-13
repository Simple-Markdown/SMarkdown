package indi.midreamsheep.app.tre.model.mainpage.sidebar.button;

import androidx.compose.ui.graphics.ImageBitmap;
import indi.midreamsheep.app.tre.api.Display;
import indi.midreamsheep.app.tre.context.mainpage.TREMainPageContext;


/**
 * 开始界面的右侧按钮
 * 用于管理开始界面的右侧按钮，通过ioc容器进行注入
 * 其子类必须使用 {@link indi.midreamsheep.app.tre.api.annotation.mainpage.MainPageButton} 进行标记
 * */
public interface TREMainPageButton {
    /**
     * 获取按钮的名称，用于显示在按钮的上侧
     * */
    String getButtonName();
    /**
     * 获取按钮的描述，用于显示在按钮的下侧
     * */
    String getDescription();
    /**
     * 获取按钮的图标，用于显示在按钮的左侧
     * */
    ImageBitmap getIcon();
    /**
     * 当按钮被点击时触发
     * @param context 开始界面的上下文
     * */
    void onClick(TREMainPageContext context);
}