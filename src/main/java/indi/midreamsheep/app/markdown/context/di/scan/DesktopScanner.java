package indi.midreamsheep.app.markdown.context.di.scan;

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
        log.info("scan paths:{}",root);
        Set<Class<?>> result = new HashSet<>();
        //扫描build/classes目录下的所有class(用于编码)
        result.addAll(scanClasses(root + "/build/classes/java/main/",root + "/build/classes/java/main/"));
        result.addAll(scanClasses(root + "/build/classes/kotlin/main/",root + "/build/classes/kotlin/main/"));
        //扫描核心包
        log.info("scan core.jar:{}",root + "/core.jar");
        result.addAll(scanAJar(root + "/core.jar"));
        //扫描libs目录下的所有jar包
        log.info("scan libs:{}",root + "/libs");
        result.addAll(scanJars(root + "/libs"));
        //扫描plugins下的插件
        log.info("scan plugins:{}",root + "/plugins");
        result.addAll(scanJars(root + "/plugins"));
        PluginScannerTool.PluginConfig pluginLoading = PluginScannerTool.getPluginLoading();
        for (String name : pluginLoading.getName()) {
            log.info("scan plugin:{}",name);
            result.addAll(scanJars(root + "/plugins/"+name+"/"));
        }
        log.info("scan success,find {} classes",result.size());
        return result;
    }

    private Set<Class<?>> scanJars(String dictionary){
        File rootDictionary = new File(dictionary);
        if (rootDictionary.exists() && rootDictionary.isDirectory()){
            File[] files = rootDictionary.listFiles();
            Set<Class<?>> classes = new HashSet<>();
            if(files==null){
                return classes;
            }

            for (File file : files) {
                if (file.getName().endsWith(".jar")){
                    classes.addAll(scanAJar(file.getAbsolutePath()));
                }
                if (file.isDirectory()){
                    classes.addAll(Objects.requireNonNull(scanJars(file.getAbsolutePath())));
                }
            }
            return classes;
        }
        return new HashSet<>();
    }

    private Set<Class<?>> scanAJar(String path){
        Set<Class<?>> classes = new HashSet<>();
        try {
            // 是jar包使用JarInputStream
            JarInputStream jarInputStream = new JarInputStream(new FileInputStream(path));
            JarEntry nextJarEntry = jarInputStream.getNextJarEntry();
            // 遍历
            while (null != nextJarEntry) {
                String name = nextJarEntry.getName();
                // 如果是指定包名下的文件并且是.class结尾，那就保存它
                if (name.endsWith(".class")) {
                    classes.add(Class.forName(name.replace(".class", "").replaceAll("/", ".")));
                }
                nextJarEntry = jarInputStream.getNextJarEntry();
            }
        } catch (IOException | ClassNotFoundException|ExceptionInInitializerError|NoClassDefFoundError ignored) {}
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
