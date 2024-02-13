package indi.midreamsheep.app.tre.model.setting.settinggroup;

import indi.midreamsheep.app.tre.service.ioc.di.inject.listdi.annotation.ListInjector;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 设置组管理器
 * 用于管理设置组，通过ioc容器进行注入
 * */
@Comment
@Data
public class SettingGroupManager {
    @ListInjector(target = "SettingGroup" )
    List<TRESettingGroup> settingGroups = new LinkedList<>();
}