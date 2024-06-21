package indi.midreamsheep.app.tre.api.annotation.shortcut;

import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector;
import indi.midreamsheep.app.tre.model.listener.shortcut.TREShortcutKeyHandler;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 用于标记一个类是处理编辑器快捷键的类
 * @author midreamsheep
 * 标记类必须继承 {@link TREShortcutKeyHandler}
 * */
@Comment
@ListInjector(target = "EditorShortcut")
@Target({ElementType.TYPE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface EditorShortcut {
}
