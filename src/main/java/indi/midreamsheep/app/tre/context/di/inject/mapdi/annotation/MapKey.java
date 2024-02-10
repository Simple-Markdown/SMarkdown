package indi.midreamsheep.app.tre.context.di.inject.mapdi.annotation;

import live.midreamsheep.frame.sioc.scan.annotation.meta.SIocFlag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记一个被Comment和MapInject标记的类被注入到map
 * 用于标记字段的key值
 * @author midreamsheep
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
@SIocFlag
public @interface MapKey {
    /**
     * 用于表示注入到map的哪个key
     */
    String value();
}
