package indi.midreamsheep.app.markdown.context.di.inject.mapdi.annotation;

import live.midreamsheep.frame.sioc.scan.annotation.meta.SIocFlag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
@SIocFlag
public @interface MapInjector {
    /**
     * 用于表示注入到哪个map
     */
    String target() default "markdown";

    /**
     * 用于表示注入到map的哪个key
     */
    String key() default "";
}
