package indi.midreamsheep.app.tre.service.window;

import indi.midreamsheep.app.tre.api.Display;
import indi.midreamsheep.app.tre.service.ioc.tre.TREIocWithCatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * tre window,用于管理窗口
 * */
@Data
@Slf4j
public abstract class TREWindow {

    protected String name;
    protected TREDesktopWindowService treDesktopWindowService = TREIocWithCatch.getBean(TREDesktopWindowService.class);

    public abstract Display getDisplay();
    /**
     * 关闭窗口
     * */
    public void close(){
        treDesktopWindowService.removeWindow(name);
        log.info("closeWindow:{}",getName());
    }

    public TREWindow(String name) {
        this.name = name;
    }

    public void register(){
        treDesktopWindowService.registerWindow(this);
        log.info("registerWindow:{}",getName());
    }
}