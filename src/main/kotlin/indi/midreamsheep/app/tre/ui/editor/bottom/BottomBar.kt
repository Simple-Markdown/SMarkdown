package indi.midreamsheep.app.tre.ui.editor.bottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import indi.midreamsheep.app.tre.context.editor.TREEditorContext

@Composable
fun bottomBar(
    context:TREEditorContext
) {
    Divider()
    Box(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = context.bottomBarViewModel.stateString.value,
            modifier = Modifier.align(Alignment.CenterEnd),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}