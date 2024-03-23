package indi.midreamsheep.app.tre.model.editor.block;

import indi.midreamsheep.app.tre.api.Display;
import indi.midreamsheep.app.tre.context.editor.TREEditorContext;

public interface TREBlock {
    /**
     * 从上次获取焦点的位置获取焦点
     * */
    void focus();
    /**
     * 从最后获取焦点
     * */
    default void focusFromLast(){
        focus();
    }
    /**
     * 从开始获取焦点
     * */
    default void focusFormStart(){
        focus();
    }
    /**
     * 释放焦点
     * */
    void releaseFocus();
    /**
     * 获取当前的composable
     * */
    Display getComposable(TREEditorContext context);
    /**
     * 获取当前的内容
     * */
    String getContent();
    TREBlockState getLineState();
}
