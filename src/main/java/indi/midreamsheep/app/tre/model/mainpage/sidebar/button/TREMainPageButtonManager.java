package indi.midreamsheep.app.tre.model.mainpage.sidebar.button;

import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 开始界面的右侧按钮管理器
 * 用于管理开始界面的右侧按钮，通过ioc容器进行注入
 * */
@Comment
@Data
public  class TREMainPageButtonManager {
    @ListInjector(target = "MainPageButtons")
    List<TREMainPageButton> buttons = new LinkedList<>();
}