package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.quote

import androidx.compose.ui.input.key.Key
import indi.midreamsheep.app.tre.model.listener.shortcut.checker.TREShortcutKeyStrongChecker
import indi.midreamsheep.app.tre.shared.frame.engine.context.manager.TREShortcutEvent

class QuoteListenerManager: TREShortcutEvent() {

    override fun keyEvent(): Boolean {
        if (quoteKeyEvent()) return true
        return super.keyEvent()
    }

    private fun quoteKeyEvent():Boolean{
        if (context.keyManager.match(TREShortcutKeyStrongChecker(Key.DirectionUp.keyCode))){
            var currentContext = context
            while (true){
                if (currentContext.blockManager.getCurrentBlockIndex()==0){
                    if(currentContext.parentContext==null) return false
                    currentContext = currentContext.parentContext!!
                    continue
                }
                break
            }
            //currentContext.blockManager.focusBlock(currentContext.blockManager.getCurrentBlockIndex()-1)
            return true
        }
        return false
    }
}

