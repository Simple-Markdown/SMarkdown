package indi.midreamsheep.app.markdown.tool.context

import indi.midreamsheep.app.markdown.context.SChatApplicationContext

fun <T>getBean(aClass: Class<T>):T  {
    return SChatApplicationContext.getApplicationContext().getBean(aClass)
}