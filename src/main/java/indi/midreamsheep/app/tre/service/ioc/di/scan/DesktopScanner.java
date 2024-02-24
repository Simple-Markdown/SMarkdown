package indi.midreamsheep.app.tre.service.ioc.di.scan;

import cn.hutool.core.io.FileUtil;
import indi.midreamsheep.app.tre.constant.AppPathConstant;
import indi.midreamsheep.app.tre.service.ioc.di.scan.jarloader.TREJarLoader;
import live.midreamsheep.frame.sioc.scan.annotation.meta.ProcessorFlag;
import live.midreamsheep.frame.sioc.scan.annotation.meta.SIocFlag;
import live.midreamsheep.frame.sioc.scan.clazz.ClassMetaDefinition;
import live.midreamsheep.frame.sioc.scan.clazz.ClassMetaDefinitionImpl;
import live.midreamsheep.frame.sioc.scan.clazz.annotation.AnnotationInfo;
import live.midreamsheep.frame.sioc.scan.inter.ClassesAbstractScanner;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * 用于给桌面端的ioc扫描器，用于扫描所有的class文件
 * 1. 扫描build/classes目录下的所有class(用于编码)
 * 2. 扫描核心包
 * 3. 扫描libs目录下的所有jar包
 * 4. 扫描plugins下的插件
 * @author midreamsheep
 * */
@Slf4j
public class DesktopScanner extends ClassesAbstractScanner {
    @Override
    public Set<Class<?>> doScan() {
        log.info("start scan classes: {}", AppPathConstant.ROOT_PATH);
        //获取缓存
        Set<String> aCatch = getCatch();
        log.debug("catch:{}",aCatch);
        boolean useCatch = !aCatch.isEmpty();
        //进行扫描
        //扫描核心包
        log.info("scan core.jar:{}", AppPathConstant.CORE_JAR_PATH);
        Set<Class<?>> result = scanCoreJar(useCatch,aCatch);

        //扫描libs目录下的所有jar包
        log.info("scan libs:{}", AppPathConstant.LIBS_PATH);
        result.addAll(scanJars(AppPathConstant.LIBS_PATH,useCatch,aCatch));

        //扫描plugins下的插件
        log.info("scan plugins:{}", AppPathConstant.PLUGIN_PATH);

        PluginScannerTool.PluginConfig pluginLoading = PluginScannerTool.getPluginLoading();
        for (String name : pluginLoading.getName()) {
            log.info("scan plugin:{} location:{}",name, AppPathConstant.PLUGIN_PATH+File.separator+name+File.separator);
            result.addAll(scanJars(AppPathConstant.PLUGIN_PATH+File.separator+name+File.separator,useCatch,aCatch));
        }

        log.info("scan success,find {} classes",result.size());
        if(!useCatch){
            outputCatch(result);
        }
        return result;
    }

    private void outputCatch(Set<Class<?>> result) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Class<?> aClass : result) {
            if (parse(aClass)!=null) {
                stringBuilder.append(aClass.getName()).append("\n");
            }
        }
        FileUtil.writeUtf8String(stringBuilder.toString(), AppPathConstant.CACHE_SCANNER_PATH);
    }

    private ClassMetaDefinition parse(Class<?> aClass) {
        ClassMetaDefinition classMetaDefinition = new ClassMetaDefinitionImpl(aClass);
        classMetaDefinition.initAnnotationInfo();
        AnnotationInfo annotationInfo = classMetaDefinition.getAnnotationInfo();
        if (annotationInfo.getAnnotation(SIocFlag.class) == null && annotationInfo.getAnnotation(ProcessorFlag.class) == null) {
            return null;
        } else {
            return classMetaDefinition;
        }
    }

    private Set<String> getCatch() {
        Set<String> aCatch = new HashSet<>();
        File file = new File(AppPathConstant.CACHE_SCANNER_PATH);
        if (!file.exists()){
            return aCatch;
        }
        //解析json
        String[] split = FileUtil.readUtf8String(file).split("\n");
        for (String s : split) {
            if (s.isEmpty()) {
                continue;
            }
            aCatch.add(s);
        }
        return aCatch;
    }

    private Set<Class<?>> scanJars(String dictionary,boolean useCatch,Set<String> aCatch){
        File rootDictionary = new File(dictionary);
        if (rootDictionary.exists() && rootDictionary.isDirectory()){
            File[] files = rootDictionary.listFiles();
            Set<Class<?>> classes = new HashSet<>();
            if(files==null){
                return classes;
            }

            for (File file : files) {
                if (file.getName().endsWith(".jar")){
                    classes.addAll(TREJarLoader.LoadAJar(file.getAbsolutePath(),  aCatch,useCatch));
                }
                if (file.isDirectory()){
                    classes.addAll(Objects.requireNonNull(scanJars(file.getAbsolutePath(),useCatch,aCatch)));
                }
            }
            return classes;
        }
        return new HashSet<>();
    }

    private Set<Class<?>> scanCoreJar(boolean useCatch, Set<String> aCatch){

        Set<Class<?>> classes = new HashSet<>();
        if(useCatch){
            for (String s : aCatch) {
                try {
                    Class<?> aClass = Class.forName(s);
                    classes.add(aClass);
                    log.debug("load class:{} success",aClass.getName());
                } catch (Throwable e) {
                    log.debug("load class:{} failed error:{}",s,e.getMessage());
                }
            }
            return classes;
        }

        if (!new File(AppPathConstant.CORE_JAR_PATH).exists()){
            return classes;
        }
        JarInputStream jarInputStream;
        JarEntry nextJarEntry;
        try {
            jarInputStream = new JarInputStream(new FileInputStream(AppPathConstant.CORE_JAR_PATH));
            nextJarEntry = jarInputStream.getNextJarEntry();
        } catch (IOException e) {
            log.error(e.getMessage());
            return classes;
        }
        while (null != nextJarEntry) {
                String name = nextJarEntry.getName();
                if (name.endsWith(".class")) {
                    try {
                        classes.add(Class.forName(name.replace(".class", "").replaceAll("/", ".")));
                    } catch (Throwable e) {
                        log.debug("load class:{} failed error:{}",name,e.getMessage());
                    }
                }
            try {
                nextJarEntry = jarInputStream.getNextJarEntry();
            } catch (IOException e) {
                log.debug(e.getMessage());
            }
        }
        log.debug("scan jar:{} success,find {} classes", AppPathConstant.CORE_JAR_PATH,classes.size());
        return classes;
    }
}
