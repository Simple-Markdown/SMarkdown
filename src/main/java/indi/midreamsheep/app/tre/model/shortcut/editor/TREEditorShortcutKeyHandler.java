package indi.midreamsheep.app.tre.model.shortcut.editor;

import indi.midreamsheep.app.tre.context.TREContext;
import indi.midreamsheep.app.tre.context.editor.TREEditorContext;
import indi.midreamsheep.app.tre.model.shortcut.TREShortcutKeyHandler;

public abstract class TREEditorShortcutKeyHandler implements TREShortcutKeyHandler {
    @Override
    public void action(TREContext context) {
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
