package indi.midreamsheep.app.tre.shared.tool.id

import cn.hutool.core.util.IdUtil
import indi.midreamsheep.app.tre.shared.api.tre.TREClassId
import indi.midreamsheep.app.tre.shared.api.tre.TREObjectId

val objectIdMap = mutableMapOf<TREObjectId, Long>()
val classIdMap = mutableMapOf<Class<out TREClassId>, Long>()

fun getIdFromPool(treId: TREObjectId): Long {
    if(!objectIdMap.containsKey(treId)){
        objectIdMap[treId] = generateId()
    }
    return objectIdMap[treId]!!
}

fun removeIdFromPool(treId: TREObjectId): Boolean {
    if(!objectIdMap.containsKey(treId)){
        return false
    }
    return objectIdMap.remove(treId) != null
}

fun getIdFromPool(targetClass: Class<out TREClassId>): Long {
    if(!classIdMap.containsKey(targetClass)){
        classIdMap[targetClass] = generateId()
    }
    return classIdMap[targetClass]!!
}

fun generateId() = IdUtil.getSnowflake().nextId()