package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.table

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.desktop.page.editor.TREEditorWindowObserverManager
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext
import indi.midreamsheep.app.tre.shared.frame.manager.blockmanager.TREBlockManagerImpl

class TableBlockBuilder(
    val originalData: List<String>
) {
    public fun build(editorContext: TREEditorContext, rowSize: Int, columnSize: Int):TableContextBlock{
        // 构建上下文
        val tableBlockContext = TREEditorContext(
            parentContext =  editorContext,
            keyManager = editorContext.keyManager,
            blockManager = TREBlockManagerImpl(),
            treObserverManager = TREEditorWindowObserverManager(),
            treShortcutEvent = TableBlockShortcutEvent(),
            metaData = editorContext.metaData,
        )
        // 构建tableBlock并传入值
        val tableBlock = TableContextBlock(editorContext,tableBlockContext,originalData,rowSize,columnSize)
        // 绑定block
        tableBlockContext.block = tableBlock
        return tableBlock
    }
}

@Composable
fun TableBuilderDialog(
    originalRowSizeSize:Int,
    onConfirmation: (Int,Int) -> Unit
) {
    var rowSize by remember { mutableStateOf(originalRowSizeSize) }
    var columnSize by remember { mutableStateOf(1) }
    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 构建输入框
            Text(
                text = "please define your table data",
                modifier = Modifier.padding(16.dp),
            )
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "row",
                        modifier = Modifier.padding(16.dp),
                    )
                    BasicTextField(
                        value = rowSize.toString(),
                        onValueChange = {
                            val toIntOrNull = it.toIntOrNull()
                            if (toIntOrNull!=null){
                                rowSize = toIntOrNull
                            }
                        },
                        modifier = Modifier.border(1.dp, Color.Blue.copy(alpha = 0.5f), RoundedCornerShape(5)).padding(5.dp)
                    )
                    Text(
                        text = "column",
                        modifier = Modifier.padding(16.dp),
                    )
                    BasicTextField(
                        value = columnSize.toString(),
                        onValueChange = {
                            val toIntOrNull = it.toIntOrNull()
                            if (toIntOrNull!=null){
                                columnSize = toIntOrNull
                            }
                        },
                        modifier = Modifier.border(1.dp, Color.Blue.copy(alpha = 0.5f), RoundedCornerShape(5)).padding(5.dp)
                    )
                }

            }
            Row(
                modifier = Modifier
            ) {
                Spacer(Modifier.weight(1f))
                TextButton(
                    onClick = { onConfirmation(rowSize,columnSize) },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}