package indi.midreamsheep.app.tre.service.ioc.tre;

import indi.midreamsheep.app.tre.service.ioc.TREApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class TREIocWithCatch {

    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();

    public static <T> T getBean(Class<T> clazz){
        if(BEAN_MAP.containsKey(clazz)){
            return (T) BEAN_MAP.get(clazz);
        }
        T bean = TREApplicationContext.getApplicationContext().getBean(clazz);
        BEAN_MAP.put(clazz,bean);
        return bean;
    }
}