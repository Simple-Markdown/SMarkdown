package indi.midreamsheep.app.tre.shared.tool.id

import cn.hutool.core.util.IdUtil
import indi.midreamsheep.app.tre.shared.api.tre.TREId

val idMap = mutableMapOf<TREId, Long>()

fun getIdFromPool(treId: TREId): Long {
    if(!idMap.containsKey(treId)){
        idMap[treId] = generateId()
    }
    return idMap[treId]!!
}

fun removeIdFromPool(treId: TREId): Boolean {
    if(!idMap.containsKey(treId)){
        return false
    }
    return idMap.remove(treId) != null
}

fun generateId() = IdUtil.getSnowflake().nextId()