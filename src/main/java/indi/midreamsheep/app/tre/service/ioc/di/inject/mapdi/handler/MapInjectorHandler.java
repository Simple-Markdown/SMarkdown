package indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.handler;

import live.midreamsheep.frame.sioc.api.context.application.ApplicationContext;
import live.midreamsheep.frame.sioc.api.handle.AbstractContextHandler;
import live.midreamsheep.frame.sioc.api.handle.HandlerLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@SuppressWarnings("unchecked")
public class MapInjectorHandler extends AbstractContextHandler {

    private Field field;
    private Class<?> clazz;
    private List<InjectorData> addClassed;
    @Override
    public void handle(ApplicationContext applicationContext) {
        try {
            field.setAccessible(true);
            Object o = field.get(applicationContext.getBean(clazz));
            if (!(o instanceof Map map)) {
                return;
            }

            if (Map.class.isAssignableFrom(field.getType())) {
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType parameterizedType) {
                    switch (parameterizedType.getActualTypeArguments()[0].getTypeName()){
                        case "java.lang.String", "kotlin.String":
                            injectString(applicationContext,map);
                            break;
                        case "java.lang.Character","kotlin.Char":
                            injectChar(applicationContext,map);
                            break;
                        default:
                            System.out.println("不支持的类型" + parameterizedType.getActualTypeArguments()[0].getTypeName());
                            //injectChar(applicationContext,map);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private void injectChar(ApplicationContext applicationContext, Map map) {
        for (InjectorData injectorData : addClassed) {
            char start = injectorData.getKey().charAt(0);
            if (!map.containsKey(start)){
                map.put(start,new LinkedList<>());
            }
            ((List)map.get(start)).add(applicationContext.getBean(injectorData.getClazz()));
        }
    }

    private void injectString(ApplicationContext applicationContext, Map map){
        for (InjectorData injectorData : addClassed) {
            if (!map.containsKey(injectorData.getKey())){
                map.put(injectorData.getKey(),new LinkedList<>());
            }
            ((List)map.get(injectorData.getKey())).add(applicationContext.getBean(injectorData.getClazz()));
        }
    }

    @Override
    public HandlerLevel getHandlerLevel() {
        return HandlerLevel.DECORATE;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class InjectorData{
        private String key;
        private Class<?> clazz;
    }
}
