package indi.midreamsheep.app.tre.context.di.inject.listdi.handler;

import live.midreamsheep.frame.sioc.api.context.application.ApplicationContext;
import live.midreamsheep.frame.sioc.api.handle.AbstractContextHandler;
import live.midreamsheep.frame.sioc.api.handle.HandlerLevel;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;

@AllArgsConstructor
public class ListInjectorHandler extends AbstractContextHandler {

    private Field field;
    private Class<?> clazz;
    private List<Class<?>> addClass;
    @Override
    @SuppressWarnings("unchecked")
    public void handle(ApplicationContext applicationContext) {
        try {
            field.setAccessible(true);
            Object o = field.get(applicationContext.getBean(clazz));
            if (!(o instanceof List list)) {
                return;
            }
            for (Class<?> aClass : addClass) {
                list.add(applicationContext.getBean(aClass));
            }
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public HandlerLevel getHandlerLevel() {
        return HandlerLevel.DECORATE;
    }
}
