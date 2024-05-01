package indi.midreamsheep.app.tre.shared.render.prebutton

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.TREComposable

@FunctionalInterface
fun interface TRELinePreButton {
    fun getPreButton(): TREComposable
}

class TREDefaultLinePreButton : TRELinePreButton {
    override fun getPreButton(): TREComposable {
        return TREComposable{
            {
                Box(){
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    }
}