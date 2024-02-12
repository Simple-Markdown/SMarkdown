package indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.processor;

import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapKey;
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapInject;
import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.handler.MapInjectorHandler;
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
public class MapInjectorProcessor extends DecorateProcessor {


    Map<String,List<MapInjectorHandler.InjectorData>> map = new HashMap<>();

    @Override
    public void process(ClassMetaDefinition classMetaDefinition, List<ContextHandler> list) {
        processor.process(classMetaDefinition,list);
        processClassFlag(classMetaDefinition,list);
        processFieldFlag(classMetaDefinition,list);
    }

    private void processClassFlag(ClassMetaDefinition classMetaDefinition, List<ContextHandler> list){
        MapInject annotation = classMetaDefinition.getAnnotationInfo().getAnnotation(MapInject.class);
        if (annotation==null){
            return;
        }
        String target = annotation.value();
        if (!map.containsKey(target)) {
            map.put(target,new LinkedList<>());
        }
        MapKey key = classMetaDefinition.getAnnotationInfo().getAnnotation(MapKey.class);
        if (key==null){
            throw new RuntimeException("the class tagged by MapInject must be tagged by MapKey too!");
        }
        map.get(target).add(new MapInjectorHandler.InjectorData(key.value(),classMetaDefinition.getOwnClass()));
    }

    private void processFieldFlag(ClassMetaDefinition classMetaDefinition, List<ContextHandler> list){
        for (FieldInter fieldInter : classMetaDefinition.getFieldInfo().getFieldInterList()) {
            MapInject annotation = fieldInter.getAnnotationInfo().getAnnotation(MapInject.class);
            if (annotation == null) {
                continue;
            }
            if (!map.containsKey(annotation.value())){
                map.put(annotation.value(),new LinkedList<>());
            }
            list.add(new MapInjectorHandler(fieldInter.getField(),classMetaDefinition.getOwnClass(),map.get(annotation.value())));
        }
    }
}
