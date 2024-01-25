package indi.midreamsheep.app.markdown.context.setting;

import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector;
import indi.midreamsheep.app.markdown.setting.AbstractConfig;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Comment
@Data
public class ConfigManager {

        @ListInjector
        private List<AbstractConfig> configs = new LinkedList<>();

}
