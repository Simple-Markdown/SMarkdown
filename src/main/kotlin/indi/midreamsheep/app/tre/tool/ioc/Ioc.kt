package indi.midreamsheep.app.tre.tool.ioc

import indi.midreamsheep.app.tre.service.ioc.tre.TREIocWithCatch

fun <T>getBean(aClass: Class<T>):T  {
    return TREIocWithCatch.getBean(aClass)
}