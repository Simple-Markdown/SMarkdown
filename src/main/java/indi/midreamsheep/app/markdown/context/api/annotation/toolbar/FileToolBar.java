package indi.midreamsheep.app.markdown.context.api.annotation.toolbar;

import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Comment
@ListInjector(target = "FileToolBar" )
@Target({ElementType.TYPE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface FileToolBar {
}
