package indi.midreamsheep.app.tre.desktop.page.editor.ui.bottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import indi.midreamsheep.app.tre.desktop.page.editor.TRELocalEditorWindowContext

@Composable
fun bottomBar() {
    val context = TRELocalEditorWindowContext.LocalContext.current
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