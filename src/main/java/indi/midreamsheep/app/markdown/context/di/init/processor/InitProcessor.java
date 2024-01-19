package indi.midreamsheep.app.markdown.context.di.init.processor;

import indi.midreamsheep.app.markdown.context.di.init.annotation.Init;
import indi.midreamsheep.app.markdown.context.di.init.handler.InitHandler;
import live.midreamsheep.frame.sioc.api.handle.ContextHandler;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import live.midreamsheep.frame.sioc.scan.annotation.meta.ProcessorFlag;
import live.midreamsheep.frame.sioc.scan.clazz.ClassMetaDefinition;
import live.midreamsheep.frame.sioc.scan.clazz.field.FieldInter;
import live.midreamsheep.frame.sioc.scan.clazz.method.MethodInter;
import live.midreamsheep.frame.sioc.scan.processor.core.decorate.DecorateProcessor;

import java.lang.reflect.Method;
import java.util.List;

@ProcessorFlag({Comment.class})
public class InitProcessor extends DecorateProcessor {

    @Override
    public void process(ClassMetaDefinition classMetaDefinition, List<ContextHandler> list) {
        processor.process(classMetaDefinition,list);
        for (MethodInter<Method> methodMethodInter : classMetaDefinition.getMethodInfo().getMethodInterList()) {
            if (methodMethodInter.getAnnotationInfo().getAnnotation(Init.class) != null) {
                list.add(new InitHandler(methodMethodInter.getMethod()));
            }
        }

    }
}
