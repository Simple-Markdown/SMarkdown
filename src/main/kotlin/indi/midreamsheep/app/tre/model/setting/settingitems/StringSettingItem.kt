package indi.midreamsheep.app.tre.model.setting.settingitems

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.api.Display
import indi.midreamsheep.app.tre.model.setting.setting.TRESettingItem

class StringSettingItem(data: String, private var displayContent: String) :
    TRESettingItem<String> {

    private var data = mutableStateOf("")

    init {
        this.data.value = data
    }

    override fun getData(): String {
        return data.value
    }

    override fun getDisplay(): Display {
        return Display {
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
                        data.value = it
                    },
                    modifier = Modifier.widthIn(min=50.dp)
                )
            }
        }
    }

    override fun getName(): String {
        return displayContent
    }

    override fun setName(name: String) {
        displayContent = name
    }


    /**
     * 设置数据
     * */
    override fun setData(data: Any) {
        if (data is Int) {
            this.data.value = data.toString()
        }
    }
}