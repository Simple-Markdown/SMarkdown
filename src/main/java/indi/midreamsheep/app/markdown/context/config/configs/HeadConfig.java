package indi.midreamsheep.app.markdown.context.config.configs;

import indi.midreamsheep.app.markdown.context.config.AbstractStandardConfig;
import indi.midreamsheep.app.markdown.context.di.inject.listdi.annotation.ListInjector;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Comment
@Data
@ListInjector
public class HeadConfig extends AbstractStandardConfig {

    private String head1Size = "20";
    private String head2Size = "30";
    private String head3Size = "40";
    private String head4Size = "50";
    private String head5Size = "60";
    private String head6Size = "70";

}
