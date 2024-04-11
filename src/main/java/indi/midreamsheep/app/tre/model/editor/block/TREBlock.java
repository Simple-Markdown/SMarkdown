package indi.midreamsheep.app.tre.model.editor.block;

import indi.midreamsheep.app.tre.api.Display;
import indi.midreamsheep.app.tre.api.TREComposable;
import indi.midreamsheep.app.tre.context.editor.TREEditorContext;

public interface TREBlock {

    /**
     * 获取Block的唯一id
     * id默认通过雪花算法生成
     * */
    long getId();

    /**
     * 从上次获取焦点的位置获取焦点
     * */
    void focus();
    /**
     * 从最后获取焦点
     * */
    void focusFromLast();
    /**
     * 从开始获取焦点
     * */
    void focusFormStart();
    /**
     * 释放焦点
     * */
    void releaseFocus();
    /**
     * 获取当前的composable
     * */
    TREComposable getDisplay(TREEditorContext context);
    /**
     * 获取前置按钮
     * */
    TREComposable getPreButton();

    /**
     * 获取当前的内容
     * */
    String getContent();
    TREBlockState getLineState();
}