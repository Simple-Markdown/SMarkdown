package indi.midreamsheep.app.tre.api.annotation.toolbar.toolbars;

import indi.midreamsheep.app.tre.desktop.page.editor.model.toolbar.SubBarItem;
import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 用于标记一个类是编辑器file层级下的工具栏
 * @author midreamsheep
 * 标记类必须继承 {@link SubBarItem}
 * */
@Comment
@ListInjector(target = "FileToolBar" )
@Target({ElementType.TYPE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface FileToolBar {
}
