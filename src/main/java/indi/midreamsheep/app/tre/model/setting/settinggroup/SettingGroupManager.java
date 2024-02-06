package indi.midreamsheep.app.tre.model.setting.settinggroup;

import indi.midreamsheep.app.tre.context.di.inject.listdi.annotation.ListInjector;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Comment
@Data
public class SettingGroupManager {
    @ListInjector(target = "SettingGroup" )
    List<SettingGroup> settingGroups = new LinkedList<>();
}