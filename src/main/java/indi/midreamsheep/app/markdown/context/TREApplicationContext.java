package indi.midreamsheep.app.markdown.context;


import indi.midreamsheep.app.markdown.context.di.scan.DesktopScanner;
import live.midreamsheep.frame.sioc.api.builder.application.ApplicationContextBuilder;
import live.midreamsheep.frame.sioc.api.context.application.ApplicationContext;
import live.midreamsheep.frame.sioc.core.context.CoreBeanHandlerInjector;
import live.midreamsheep.frame.sioc.core.context.application.CoreApplicationContext;
import live.midreamsheep.frame.sioc.core.context.factory.CoreBeanFactory;
import live.midreamsheep.frame.sioc.scan.PackageBeanDefinitionsFactory;
import live.midreamsheep.frame.sioc.scan.parse.CoreClassParserToDefinition;

public class TREApplicationContext {

    private static final ApplicationContext applicationContext;
    static {
        ApplicationContextBuilder applicationContextBuilder = new ApplicationContextBuilder();

        PackageBeanDefinitionsFactory packageBeanDefinitionsFactory = new PackageBeanDefinitionsFactory(new DesktopScanner(), new CoreClassParserToDefinition());
        applicationContextBuilder.setClassbeanHandlerFactory(packageBeanDefinitionsFactory);

        //设置执行器
        applicationContextBuilder.setBeanHandlerInjector(new CoreBeanHandlerInjector());

        //设置applicationContext
        applicationContextBuilder.setApplicationContext(new CoreApplicationContext(new CoreBeanFactory()));

        applicationContext = applicationContextBuilder.build();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
