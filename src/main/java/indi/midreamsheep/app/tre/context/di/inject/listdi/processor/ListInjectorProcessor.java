package indi.midreamsheep.app.tre.context.di.inject.listdi.processor;

import indi.midreamsheep.app.tre.context.di.inject.listdi.annotation.ListInjector;
import indi.midreamsheep.app.tre.context.di.inject.listdi.handler.ListInjectorHandler;
import live.midreamsheep.frame.sioc.api.handle.ContextHandler;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import live.midreamsheep.frame.sioc.scan.annotation.meta.ProcessorFlag;
import live.midreamsheep.frame.sioc.scan.clazz.ClassMetaDefinition;
import live.midreamsheep.frame.sioc.scan.clazz.field.FieldInter;
import live.midreamsheep.frame.sioc.scan.processor.core.decorate.DecorateProcessor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@ProcessorFlag({Comment.class})
public class ListInjectorProcessor extends DecorateProcessor {


    Map<String,List<Class<?>>> map = new HashMap<>();

    @Override
    public void process(ClassMetaDefinition classMetaDefinition, List<ContextHandler> list) {
        processor.process(classMetaDefinition,list);
        processClassFlag(classMetaDefinition,list);
        processFieldFlag(classMetaDefinition,list);
    }

    private void processClassFlag(ClassMetaDefinition classMetaDefinition, List<ContextHandler> list){
        ListInjector annotation = classMetaDefinition.getAnnotationInfo().getAnnotation(ListInjector.class);
        if (annotation==null){
            return;
        }
        String target = annotation.target();
        if (!map.containsKey(target)) {
            map.put(target,new LinkedList<>());
        }
        map.get(target).add(classMetaDefinition.getOwnClass());
    }

    private void processFieldFlag(ClassMetaDefinition classMetaDefinition, List<ContextHandler> list){
        for (FieldInter fieldInter : classMetaDefinition.getFieldInfo().getFieldInterList()) {
            ListInjector annotation = fieldInter.getAnnotationInfo().getAnnotation(ListInjector.class);
            if (annotation == null) {
                continue;
            }
            String target = annotation.target();
            if (!map.containsKey(target)) {
                map.put(target,new LinkedList<>());
            }
            list.add(new ListInjectorHandler(fieldInter.getField(),classMetaDefinition.getOwnClass(),map.get(annotation.target())));
        }
    }
}
