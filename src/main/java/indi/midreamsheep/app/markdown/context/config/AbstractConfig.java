package indi.midreamsheep.app.markdown.context.config;

import cn.hutool.core.bean.BeanUtil;
import indi.midreamsheep.app.markdown.tool.FastJsonUtils;

import java.util.Map;

public abstract class AbstractConfig {
    protected void init(){
        Object o = FastJsonUtils.convertJsonToObject(getJsonString(), this.getClass());
        if (o != null) {
            BeanUtil.copyProperties(o, this);
        }
    }

    protected Map<String,String> getPropertyMap(){
        return FastJsonUtils.stringToCollect(FastJsonUtils.convertObjectToJSON(this));
    }

    public abstract void write();

    protected abstract String getJsonString();
}
