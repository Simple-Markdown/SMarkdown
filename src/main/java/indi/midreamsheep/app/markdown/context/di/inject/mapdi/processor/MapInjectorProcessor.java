package indi.midreamsheep.app.markdown.context.di.inject.mapdi.processor;

import indi.midreamsheep.app.markdown.context.di.inject.mapdi.annotation.MapInjector;
import indi.midreamsheep.app.markdown.context.di.inject.mapdi.handler.MapInjectorHandler;
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
        MapInjector annotation = classMetaDefinition.getAnnotationInfo().getAnnotation(MapInjector.class);
        if (annotation==null){
            return;
        }
        String target = annotation.target();
        if (!map.containsKey(target)) {
            map.put(target,new LinkedList<>());
        }
        map.get(target).add(new MapInjectorHandler.InjectorData(annotation.key(),classMetaDefinition.getOwnClass()));
    }

    private void processFieldFlag(ClassMetaDefinition classMetaDefinition, List<ContextHandler> list){
        for (FieldInter fieldInter : classMetaDefinition.getFieldInfo().getFieldInterList()) {
            MapInjector annotation = fieldInter.getAnnotationInfo().getAnnotation(MapInjector.class);
            if (annotation == null) {
                continue;
            }
            if (!map.containsKey(annotation.target())){
                map.put(annotation.target(),new LinkedList<>());
            }
            list.add(new MapInjectorHandler(fieldInter.getField(),classMetaDefinition.getOwnClass(),map.get(annotation.target())));
        }
    }
}
