package indi.midreamsheep.app.tre.api.tool.ioc

import indi.midreamsheep.app.tre.context.TREApplicationContext

fun <T>getBean(aClass: Class<T>):T  {
    return TREApplicationContext.getApplicationContext().getBean(aClass)
}