package indi.midreamsheep.app.tre.service.ioc.di.init.handler;

import live.midreamsheep.frame.sioc.api.context.application.ApplicationContext;
import live.midreamsheep.frame.sioc.api.handle.AbstractContextHandler;
import live.midreamsheep.frame.sioc.api.handle.HandlerLevel;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class InitHandler extends AbstractContextHandler {

    private Method method;

    @Override
    public void handle(ApplicationContext applicationContext) {
        try {
            method.setAccessible(true);
            method.invoke(applicationContext.getBean(method.getDeclaringClass()));
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public HandlerLevel getHandlerLevel() {
        return HandlerLevel.DECORATE;
    }
}
