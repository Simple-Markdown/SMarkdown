package indi.midreamsheep.app.tre.service.language;

import indi.midreamsheep.app.tre.constant.AppPathConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 语言资源的封装
 * */
@Slf4j
public class TRELanguageResource {

    public static final Properties languageProperties;

    static {
        languageProperties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(AppPathConstant.LANGUAGE_PATH)) {
            languageProperties.load(fileInputStream);
        }catch (IOException e){
            log.info("load language file error",e);
        }
    }

    public static String getLanguage(String key,String defaultValue){
        return languageProperties.getProperty(key,defaultValue);
    }
}
