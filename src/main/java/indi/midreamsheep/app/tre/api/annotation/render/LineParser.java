package indi.midreamsheep.app.tre.api.annotation.render;

import indi.midreamsheep.app.tre.service.ioc.di.inject.mapdi.annotation.MapInject;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 用于标记一个类是编辑器的顶级工具栏
 * @author midreamsheep
 * 标记类必须继承 {@link indi.midreamsheep.app.tre.model.toolbar.TopBarItem}
 * */
@Comment
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@MapInject("LineParser")
public @interface LineParser {
}
