package indi.midreamsheep.app.tre.context.setting;

import indi.midreamsheep.app.tre.context.di.inject.listdi.annotation.ListInjector;
import indi.midreamsheep.app.tre.model.setting.AbstractConfig;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 所有的配置管理容器
 */
@Comment
@Data
public class ConfigManager {
        @ListInjector(target = "config")
        private List<AbstractConfig> configs = new LinkedList<>();

}
