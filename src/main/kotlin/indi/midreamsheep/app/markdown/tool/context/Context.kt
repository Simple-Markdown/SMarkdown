package indi.midreamsheep.app.markdown.tool.context

import indi.midreamsheep.app.markdown.context.TREApplicationContext
import logger

fun <T>getBean(aClass: Class<T>):T  {
    logger.info("get Bean: ${aClass.simpleName}")
    return TREApplicationContext.getApplicationContext().getBean(aClass)
}