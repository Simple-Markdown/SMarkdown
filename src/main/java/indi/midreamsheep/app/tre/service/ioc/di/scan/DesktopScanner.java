package indi.midreamsheep.app.tre.service.ioc.di.scan;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import indi.midreamsheep.app.tre.constant.ProjectPathConstant;
import indi.midreamsheep.app.tre.service.ioc.di.scan.jarloader.TREJarLoader;
import live.midreamsheep.frame.sioc.scan.inter.ClassesAbstractScanner;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
        log.info("start scan classes: {}",ProjectPathConstant.ROOT_PATH);
        //获取缓存
        Set<String> aCatch = getCatch();
        log.debug("catch:{}",aCatch);
        boolean useCatch = !aCatch.isEmpty();
        //进行扫描
        //扫描核心包
        log.info("scan core.jar:{}", ProjectPathConstant.CORE_JAR_PATH);
        Set<Class<?>> result = scanCoreJar(useCatch,aCatch);

        //扫描libs目录下的所有jar包
        log.info("scan libs:{}",ProjectPathConstant.LIBS_PATH);
        result.addAll(scanJars(ProjectPathConstant.LIBS_PATH,useCatch,aCatch));

        //扫描plugins下的插件
        log.info("scan plugins:{}",ProjectPathConstant.PLUGIN_PATH);

        PluginScannerTool.PluginConfig pluginLoading = PluginScannerTool.getPluginLoading();
        for (String name : pluginLoading.getName()) {
            log.info("scan plugin:{} location:{}",name,ProjectPathConstant.PLUGIN_PATH+File.separator+name+File.separator);
            result.addAll(scanJars(ProjectPathConstant.PLUGIN_PATH+File.separator+name+File.separator,useCatch,aCatch));
        }

        log.info("scan success,find {} classes",result.size());
        return result;
    }

    @SuppressWarnings("unchecked")
    private Set<String> getCatch() {
        Set<String> aCatch = new HashSet<>();
        File file = new File(ProjectPathConstant.CACHE_SCANNER_PATH);
        if (!file.exists()){
            return aCatch;
        }
        //解析json
        aCatch.addAll(JSONUtil.toBean(FileUtil.readUtf8String(file), Set.class));
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

        if (!new File(ProjectPathConstant.CORE_JAR_PATH).exists()){
            return classes;
        }
        JarInputStream jarInputStream;
        JarEntry nextJarEntry;
        try {
            jarInputStream = new JarInputStream(new FileInputStream(ProjectPathConstant.CORE_JAR_PATH));
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
        log.debug("scan jar:{} success,find {} classes", ProjectPathConstant.CORE_JAR_PATH,classes.size());
        return classes;
    }
}
