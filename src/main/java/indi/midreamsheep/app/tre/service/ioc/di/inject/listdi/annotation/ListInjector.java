package indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation;

import live.midreamsheep.frame.sioc.scan.annotation.meta.SIocFlag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记一个被Comment标记的类被注入到list
 * 或者标记一个字段被注入list
 * 注：标记字段时，字段必须是一个list 且被实例化
 * 注：标记类时，类必须是一个被Comment标记的类
 * @author midreamsheep
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
@SIocFlag
public @interface ListInjector {
    /**
     * 用于表示注入到哪个map
     * */
    String target();
}
