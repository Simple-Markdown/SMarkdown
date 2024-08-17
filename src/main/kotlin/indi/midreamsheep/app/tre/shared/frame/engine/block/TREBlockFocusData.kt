package indi.midreamsheep.app.tre.shared.frame.engine.block

import indi.midreamsheep.app.tre.shared.api.tre.TREClassId

/**
 * 数据传递标记类，仅用于标记
 * */
interface TREBlockFocusData:TREClassId {
    companion object {
        val NONE = object : TREBlockFocusData {
            override fun getId() = TREFocusEnum.STANDARD.id
        }
    }
}