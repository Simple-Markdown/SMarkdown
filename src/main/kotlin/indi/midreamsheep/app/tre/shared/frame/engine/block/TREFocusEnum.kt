package indi.midreamsheep.app.tre.shared.frame.engine.block

import cn.hutool.core.util.IdUtil

/**
 * focus的固定typeId
 * */
enum class TREFocusEnum {
    /**从block的开头获取焦点*/
    IN_START,
    /**从一个block的结尾获取焦点*/
    IN_END,
    /**从上到下到指定的绝对坐标获取焦点*/
    IN_TARGET_POSITION_DOWN,
    /**从下到上通过绝对坐标获取焦点*/
    IN_TARGET_POSITION_UP,
    STANDARD;
    val id:Long = IdUtil.getSnowflakeNextId()
}