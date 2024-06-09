package indi.midreamsheep.app.tre.shared.frame.engine.render.prebutton

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.shared.api.display.Display

@FunctionalInterface
fun interface TRELinePreButton {
    fun getPreButton(): Display
}

class TREDefaultLinePreButton : TRELinePreButton {
    override fun getPreButton(): Display {
        return Display{
            {
                Box(){
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    }
}