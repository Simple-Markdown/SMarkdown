package indi.midreamsheep.app.tre.service.ioc;


import indi.midreamsheep.app.tre.service.ioc.di.scan.DesktopScanner;
import live.midreamsheep.frame.sioc.api.builder.application.ApplicationContextBuilder;
import live.midreamsheep.frame.sioc.api.context.application.ApplicationContext;
import live.midreamsheep.frame.sioc.scan.PackageBeanDefinitionsFactory;
import live.midreamsheep.frame.sioc.scan.parse.CoreClassParserToDefinition;
import lombok.Getter;

/**
 * IOC容器上下文的配置
 * */
public class TREApplicationContext {

    @Getter
    private static final ApplicationContext applicationContext;
    static {
        ApplicationContextBuilder applicationContextBuilder = new ApplicationContextBuilder();
        PackageBeanDefinitionsFactory packageBeanDefinitionsFactory = new PackageBeanDefinitionsFactory(new DesktopScanner(), new CoreClassParserToDefinition());
        applicationContextBuilder.setClassbeanHandlerFactory(packageBeanDefinitionsFactory);
        applicationContext = applicationContextBuilder.build();
    }

}
