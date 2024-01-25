package indi.midreamsheep.app.markdown.setting

import androidx.compose.runtime.Composable

/**
 * 设置组件接口，一个设置组件应该包含一个数据和一个渲染行composable
 * */
interface SettingItem<T> {

    /**
     * @return 返回设置组件的数据
     * */
    fun getData():T
    /**
     * 获取用于设置的界面
     * */
    fun getComposable():@Composable ()->Unit

    /**
     * 设置数据
     * */
    fun setData(data:Any)
}