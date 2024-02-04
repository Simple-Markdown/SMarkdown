package indi.midreamsheep.app.tre.context.di.init.annotation;

import live.midreamsheep.frame.sioc.scan.annotation.meta.SIocFlag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记一个方法是初始化方法
 * 将会在bean创建后执行
 * 注：标记方法不能使用di注入的参数，并不保证执行顺序
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@SIocFlag
public @interface Init {}
