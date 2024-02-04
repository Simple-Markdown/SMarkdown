package indi.midreamsheep.app.tre.context.di.scan.jarloader;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
public class TREJarLoader {
    public static List<Class<?>> LoadAJar(String jarPath, Set<String> classes, boolean useCatch) {
        log.info("load jar:{}",jarPath);
        //实例化结果
        List<Class<?>> result = new LinkedList<>();
        URL url = null;
        try {
            url = new File(jarPath).toURI().toURL();
        } catch (MalformedURLException e) {
            log.error("url error", e);
            return result;
        }

        //加载jar包
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{url})) {
            Enumeration<JarEntry> entries;
            try (JarFile jar = new JarFile(jarPath)) {
                entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (!entry.getName().endsWith(".class")) {
                        continue;
                    }
                    String className = entry.getName().replace("/", ".").replace(".class", "");
                    Class<?> aClass = null;
                    try {
                        aClass = classLoader.loadClass(className);
                    } catch (Throwable e) {
                        log.debug("class load wrong:{}", className);
                        continue;
                    }
                    if (!useCatch||classes.contains(className)) {
                        result.add(aClass);
                        log.debug("load class:{}", className);
                    }
                }
            }
        } catch (MalformedURLException e) {
            log.error("url error", e);
        } catch (IOException e) {
            log.error("io error", e);
        }
        return result;
    }
}