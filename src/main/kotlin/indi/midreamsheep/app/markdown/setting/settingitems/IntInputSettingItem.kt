package indi.midreamsheep.app.markdown.setting.settingitems

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.markdown.setting.SettingItem

class IntInputSettingItem(data: Int, private var displayContent: String) : SettingItem<Int> {

    private var data = mutableStateOf("")

    init {
        this.data.value = ""+data
    }

    override fun getData(): Int {
            return data.value.toInt()
    }

    override fun getComposable():@Composable () -> Unit {
        return {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = displayContent
                )
                Spacer(modifier = Modifier.weight(1f))
                BasicTextField(
                    value = data.value,
                    onValueChange = {
                        if (it.toIntOrNull()!=null||it==""){
                            data.value = it
                        }
                    },
                    modifier = Modifier.widthIn(min=50.dp)
                )
            }
        }
    }

    override fun setData(data: Any) {
        this.data.value = data.toString()
    }
}