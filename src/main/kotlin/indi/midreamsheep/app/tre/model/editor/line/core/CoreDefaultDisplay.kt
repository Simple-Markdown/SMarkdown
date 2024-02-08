package indi.midreamsheep.app.tre.model.editor.line.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import indi.midreamsheep.app.tre.api.Display

class CoreDefaultDisplay(val line:CoreTRELine):Display {
    @Composable
    override fun display() {
        val value = line.styleText.value
        Text(
            text = value.build(),
            style = TextStyle(
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    line.recall.invoke()
                }
        )
    }
}