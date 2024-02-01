package indi.midreamsheep.app.markdown.tool.context

import indi.midreamsheep.app.markdown.context.TREApplicationContext

fun <T>getBean(aClass: Class<T>):T  {
    return TREApplicationContext.getApplicationContext().getBean(aClass)
}