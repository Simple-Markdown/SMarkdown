package indi.midreamsheep.app.tre.service.ioc.di.scan;

import cn.hutool.core.io.FileUtil;
import indi.midreamsheep.app.tre.constant.AppPathConstant;
import live.midreamsheep.frame.sioc.api.handle.ContextHandler;
import live.midreamsheep.frame.sioc.api.handle.HandlerSet;
import live.midreamsheep.frame.sioc.scan.PackageBeanDefinitionsFactory;
import live.midreamsheep.frame.sioc.scan.inter.ClassParserToDefinition;
import live.midreamsheep.frame.sioc.scan.inter.ClassesScanner;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PackageBeanDefinitionsFactoryCatch extends PackageBeanDefinitionsFactory {
    public PackageBeanDefinitionsFactoryCatch(ClassesScanner classesScanner, ClassParserToDefinition definitionsParser) {
        super(classesScanner, definitionsParser);
    }

    @Override
    public HandlerSet generateHandlerManager() {
        HandlerSet handlerSet = super.generateHandlerManager();
        Set<Class<?>> classes = new HashSet<>();

        for (ContextHandler contextHandler : handlerSet.getContextHandlers()) {
            Collections.addAll(classes, contextHandler.toGenerate());
        }
        StringBuilder sb = new StringBuilder();
        List<Class<?>> list = classes.stream().toList();
        for (Class<?> aClass : list) {
            sb.append(aClass.getName()).append("\n");
        }
        FileUtil.writeUtf8String(sb.toString(), AppPathConstant.CACHE_SCANNER_PATH);
        return handlerSet;
    }
}
