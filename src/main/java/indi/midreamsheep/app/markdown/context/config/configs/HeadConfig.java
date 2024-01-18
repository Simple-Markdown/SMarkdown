package indi.midreamsheep.app.markdown.context.config.configs;

import indi.midreamsheep.app.markdown.context.config.AbstractConfig;
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment;

//@Comment
public class HeadConfig extends AbstractConfig {

    public HeadConfig(){
       // init();
    }

    @Override
    public void write() {
        //TODO
    }

    @Override
    protected String getJsonString() {
        String property = System.getProperty("user.dir");

        return null;
    }
}
