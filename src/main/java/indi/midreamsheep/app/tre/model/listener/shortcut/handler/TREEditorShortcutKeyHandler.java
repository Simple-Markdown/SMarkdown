package indi.midreamsheep.app.tre.model.listener.shortcut.handler;

import indi.midreamsheep.app.tre.desktop.context.TREWindowContext;
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowContext;
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyHandler;

/**
 * 编辑器快捷键处理器
 * 仅仅对上下文环境进行了限制
 * */
public abstract class TREEditorShortcutKeyHandler implements TREShortcutKeyHandler {
    @Override
    public void handle(TREWindowContext context) {
        if (context instanceof TREEditorWindowContext editorContext){
            action(editorContext);
        }
    }

    @Override
    public boolean isEnable(TREWindowContext context){
        if (context instanceof TREEditorWindowContext editorContext){
            return (editorContext.getEditorFileManager().getStateManager().getCurrentBlock() != null)&&isEnable(editorContext);
        }
        return false;
    }

    protected abstract void action(TREEditorWindowContext context);
    protected abstract boolean isEnable(TREEditorWindowContext context);
}
