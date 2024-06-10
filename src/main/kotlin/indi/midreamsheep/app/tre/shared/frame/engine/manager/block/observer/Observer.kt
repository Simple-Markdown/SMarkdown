package indi.midreamsheep.app.tre.shared.frame.engine.manager.block.observer

import indi.midreamsheep.app.tre.shared.frame.engine.manager.TREBlockManager

val observerMap = mutableMapOf<Long,MutableList<TREObserver>>()

var isInit = false

fun addObserver(
    id: Long,
    observer: TREObserver
){
    if (!observerMap.containsKey(id)){
        observerMap[id] = mutableListOf()
    }
    observerMap[id]!!.add(observer)
}

fun addObservers(
    ids: Array<Long>,
    observer: TREObserver
){
    ids.forEach {
        addObserver(it,observer)
    }
}

fun remove(
    id: Long,
    observer: TREObserver
){
    if (!observerMap.containsKey(id)||!observerMap[id]!!.contains(observer)){
        return
    }
    observerMap[id]!!.remove(observer)
    if (observerMap[id]!!.size==0){
        observerMap.remove(id)
    }
}

fun remove(
    ids: Array<Long>,
    observer: TREObserver
){
    for (id in ids) {
        remove(id,observer)
    }
}

fun remove(
    observer: TREObserver
){
    observerMap.entries.forEach {
        if (it.value.contains(observer)){
            remove(it.key,observer)
        }
    }
}

fun update(
    index:Int,
    blockManager:TREBlockManager,
    id:Long
){
    init()
    if (!observerMap.containsKey(id)){
        return
    }
    observerMap[id]!!.forEach {
        it.update(index,blockManager)
    }
}

//初始化方法
fun init(){
    if(isInit){
        return
    }
    isInit = true
}