package indi.midreamsheep.app.tre.context.di.scan;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import indi.midreamsheep.app.tre.context.di.scan.jarloader.TREJarLoader;
import live.midreamsheep.frame.sioc.di.processor.comment.CommentProcessor;
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
        String root = System.getProperty("user.dir");
        log.info("start scan classes: {}",root);
        //获取缓存
        Set<String> aCatch = getCatch(root);
        log.debug("catch:{}",aCatch);
        boolean useCatch = !aCatch.isEmpty();
        //进行扫描
        Set<Class<?>> result = new HashSet<>();

        //扫描build/classes目录下的所有class(用于编码)
        result.addAll(scanClasses(root + "/build/classes/java/main/",root + "/build/classes/java/main/"));
        result.addAll(scanClasses(root + "/build/classes/kotlin/main/",root + "/build/classes/kotlin/main/"));

        //扫描核心包
        log.info("scan core.jar:{}",root + "/core.jar");
        result.addAll(scanAJar(root + "/core.jar",useCatch,aCatch));

        //扫描libs目录下的所有jar包
        log.info("scan libs:{}",root + "/libs");
        result.addAll(scanJars(root + "/libs",useCatch,aCatch));

        //扫描plugins下的插件
        log.info("scan plugins:{}",root + "/plugins");

        PluginScannerTool.PluginConfig pluginLoading = PluginScannerTool.getPluginLoading();
        for (String name : pluginLoading.getName()) {
            log.info("scan plugin:{} location:{}",name,root + "/plugins/"+name+"/");
            result.addAll(scanJars(root + "/plugins/"+name+"/",useCatch,aCatch));
        }

        log.info("scan success,find {} classes",result.size());
        return result;
    }

    private Set<String> getCatch(String root) {
        Set<String> aCatch = new HashSet<>();
        File file = new File(root + "/cache/scanner.catch");
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

    private Set<Class<?>> scanAJar(String path, boolean useCatch, Set<String> aCatch){
        Set<Class<?>> classes = new HashSet<>();
        if (!new File(path).exists()){
            return classes;
        }
        JarInputStream jarInputStream = null;
        JarEntry nextJarEntry = null;
        try {
            jarInputStream = new JarInputStream(new FileInputStream(path));
            nextJarEntry = jarInputStream.getNextJarEntry();
        } catch (IOException e) {
            log.error(e.getMessage());
            return classes;
        }
        while (null != nextJarEntry) {
                String name = nextJarEntry.getName();
                if (name.endsWith(".class")) {
                    String className = name.replace("/", ".").replace(".class", "");
                    if (useCatch && !aCatch.contains(className)) {
                        continue;
                    }
                    try {
                        classes.add(Class.forName(name.replace(".class", "").replaceAll("/", ".")));
                    } catch (Throwable ignored) {
                        log.debug("load class:{} failed error:{}",name,ignored.getMessage());
                    }
                }
            try {
                nextJarEntry = jarInputStream.getNextJarEntry();
            } catch (IOException e) {
                log.debug(e.getMessage());
            }
        }
        log.debug("scan jar:{} success,find {} classes",path,classes.size());
        return classes;
    }

    private Set<Class<?>> scanClasses(String path,String rootPath){
        Set<Class<?>> classes = new HashSet<>();
        File rootDictionary = new File(path);
        if (rootDictionary.exists() && rootDictionary.isDirectory()){
            File[] files = rootDictionary.listFiles();
            if(files==null){
                return classes;
            }
            for (File file : files) {
                if (file.isDirectory()){
                    classes.addAll(Objects.requireNonNull(scanClasses(file.getAbsolutePath(),rootPath)));
                }
                if (file.getName().endsWith(".class")){
                    try {
                        classes.add(Class.forName(file.getAbsolutePath().replace(rootPath,"").replace(".class", "").replaceAll("/", ".")));
                    } catch (ClassNotFoundException|ExceptionInInitializerError|NoClassDefFoundError ignored) {}
                }
            }
        }
        return classes;
    }
}
