package indi.midreamsheep.app.tre.service.window;

import indi.midreamsheep.app.tre.desktop.context.TREWindowContext;
import indi.midreamsheep.app.tre.service.ioc.tre.TREIocWithCatch;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * tre window,用于管理窗口
 * */
@Data
@Slf4j
public class TREWindow {

    private TREDesktopWindowService treDesktopWindowService = TREIocWithCatch.getBean(TREDesktopWindowService.class);

    private TREWindowContext treWindowContext;

    public TREWindow(TREWindowContext treWindowContext) {
        this.treWindowContext = treWindowContext;
        this.treWindowContext.setTREWindow(this);
    }

    /**
     * 关闭窗口
     * */
    public void close(){
        treDesktopWindowService.removeWindow(this);
        log.info("closeWindow:{}",treWindowContext.getWindowTitle());
    }

    public void register(){
        treDesktopWindowService.registerWindow(this);
        log.info("registerWindow:{}",treWindowContext.getWindowTitle());
    }
}