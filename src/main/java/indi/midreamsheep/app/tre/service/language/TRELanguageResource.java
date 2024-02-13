package indi.midreamsheep.app.tre.service.language;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.io.File.separator;

/**
 * 语言资源的封装
 * */
@Slf4j
public class TRELanguageResource {

    public static final Properties languageProperties;

    static {
        languageProperties = new Properties();
            String path = System.getProperty("user.dir")+ separator+"configs"+separator + "language"+separator+ "language.properties";
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            languageProperties.load(fileInputStream);
        }catch (IOException e){
            log.error("load language file error",e);
        }
    }

    public static String getLanguage(String key,String defaultValue){
        return languageProperties.getProperty(key,defaultValue);
    }
}
