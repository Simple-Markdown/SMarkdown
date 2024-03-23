package indi.midreamsheep.app.tre.model.listener.shortcut.handler;

import indi.midreamsheep.app.tre.context.TREContext;
import indi.midreamsheep.app.tre.context.editor.TREEditorContext;
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyHandler;

/**
 * 编辑器快捷键处理器
 * 仅仅对上下文环境进行了限制
 * */
public abstract class TREEditorShortcutKeyHandler implements TREShortcutKeyHandler {
    @Override
    public void handle(TREContext context) {
        if (context instanceof TREEditorContext editorContext){
            action(editorContext);
        }
    }



    @Override
    public boolean isEnable(TREContext context) {
        return context instanceof TREEditorContext;
    }

    protected abstract void action(TREEditorContext context);
}
