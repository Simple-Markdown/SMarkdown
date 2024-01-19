package indi.midreamsheep.app.markdown.config

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import cn.hutool.core.bean.BeanUtil
import cn.hutool.json.JSONUtil

abstract class AbstractConfig {

        private var propertyMap = mutableStateMapOf<String,String>()
        
        protected fun init() {
            val jsonString = getJsonString()
            if (jsonString.isNullOrEmpty()) {
                return
            }
            val o = JSONUtil.toBean(getJsonString(), this.javaClass)
            if (o != null) {
                BeanUtil.copyProperties(o, this)
            }
        }

        fun getMap(): SnapshotStateMap<String, String> {
            if (propertyMap.size==0) {
                val jsonStr = JSONUtil.toJsonStr(this)
                for (entry in JSONUtil.toBean(jsonStr, Map::class.java)) {
                    propertyMap[entry.key.toString()] = entry.value.toString()
                }
            }
            return propertyMap
        }

        abstract fun write()

        protected abstract fun getJsonString(): String

        abstract fun getConfigName(): String
}