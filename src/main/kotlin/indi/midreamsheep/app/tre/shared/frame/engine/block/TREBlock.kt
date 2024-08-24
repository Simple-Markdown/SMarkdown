package indi.midreamsheep.app.tre.shared.frame.engine.block;

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import indi.midreamsheep.app.tre.shared.api.tre.TREObjectId
import indi.midreamsheep.app.tre.shared.frame.TREEditorContext

/**
 * tre编辑器块的基本接口
 * */
interface TREBlock:TREObjectId {
    /*快捷键相关操作*/
    /**
     * 从上次获取焦点的位置获取焦点
     * @param typeId 用于标记调用者
     * @param data 用于特殊形式的数据传递
     * */
    fun focus(typeId:Long,data: TREBlockFocusData = TREBlockFocusData.NONE)
    /**
     * 释放焦点
     * */
    fun releaseFocus()
    /**
     * 获取快捷键状态
     * */
    fun getEditorShortcutState():TREBlockShortcutState
    /**
    * 获取block组件状态，包含内部的矩阵信息
    **/
    fun getComposeState():TREBlockComposeState
    /**
     * 获取用于显示实体类，内部封装了相关用于显示的结构
     * */
    fun getTREBlockDisplay(): TREBlockDisplay
    /**
     * 获取用于输出到文件中的数据内容
     * */
    fun getOutputContent():String
    /*对管理当前block相关对象的相关操作*/
    /**
     * 获取管理当前block的管理器
     * */
    fun getBlockManager() = getEditorContext().blockManager
    /**
     * 获取当前上下文
     * */
    fun getEditorContext(): TREEditorContext
    /**
     * 重设上下文
     * */
    fun resetEditorContext(treEditorContext: TREEditorContext)
    /*用于block的声明周期处理*/
    /**
     * 当block被加入manager时调用
     * */
    fun whenInsert(){}
    /**
     * 当block被移除manager时调用
     * */
    fun whenRemove(){}
}

/**
 * 用于treBlock关于快捷键触发的焦点数据的状态类，用于告诉快捷键基本的获取焦点操作是否可用
 * 基本用法用于基本组件中的输入框是否走快捷键事件
 * */
class TREBlockShortcutState {
    var isLeftAvailable = true
    var isRightAvailable = true
    var isDownAvailable = true
    var isUpAvailable = true
    var isStandardAvailable = true
}

/**
 * treBlock的compose相关状态的获取接口
 * 每次获取都是进行一次新的计算
 * TODO 进一步完善api
 * */
interface TREBlockComposeState{
    /**
     * 获取光标的相对于窗口的绝对x位置
     * */
    fun getPointerAbsoluteXPosition():Float
    /**
     * 获取当前block顶级组件的
     * */
    fun getBlockComposeItemData(): TREBlockComposeItemData
}

// 组件状态的实体类
class TREBlockComposeItemData{

    var xWindowsPosition = 0f
    var yWindowsPosition = 0f
    // TODO

    /**
     * 通过LayoutCoordinates更新状态
     * */
    fun update(layoutCoordinates: LayoutCoordinates){
        val localToWindow = layoutCoordinates.localToWindow(Offset.Zero)
        xWindowsPosition = localToWindow.x
        yWindowsPosition = localToWindow.y
    }
}