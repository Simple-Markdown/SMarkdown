package indi.midreamsheep.app.tre.shared.frame.engine.context.block

import indi.midreamsheep.app.tre.shared.api.tre.TREClassId

/**
 * 数据传递标记类，仅用于标记
 * */
interface CustomData:TREClassId {
    companion object {
        val NONE = object : CustomData {}
    }
}