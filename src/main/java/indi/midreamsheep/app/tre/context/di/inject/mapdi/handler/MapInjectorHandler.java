package indi.midreamsheep.app.tre.context.di.inject.mapdi.handler;

import live.midreamsheep.frame.sioc.api.context.application.ApplicationContext;
import live.midreamsheep.frame.sioc.api.handle.AbstractContextHandler;
import live.midreamsheep.frame.sioc.api.handle.HandlerLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class MapInjectorHandler extends AbstractContextHandler {

    private Field field;
    private Class<?> clazz;
    private List<InjectorData> addClassed;
    @Override
    @SuppressWarnings("unchecked")
    public void handle(ApplicationContext applicationContext) {
        try {
            field.setAccessible(true);
            Object o = field.get(applicationContext.getBean(clazz));
            if (!(o instanceof Map map)) {
                return;
            }

            for (InjectorData injectorData : addClassed) {
                char start = injectorData.getKey().toCharArray()[0];
                if (!map.containsKey(start)){
                    map.put(start,new LinkedList<>());
                }
                ((List)map.get(start)).add(applicationContext.getBean(injectorData.getClazz()));
            }
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
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
