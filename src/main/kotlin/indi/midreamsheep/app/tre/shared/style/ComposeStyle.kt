package indi.midreamsheep.app.tre.shared.style

import androidx.compose.ui.Modifier

interface ComposeStyle {
    fun decorate(modifier: Modifier): Modifier
}

abstract class ComposeStyleGroup: ComposeStyle {
    override fun decorate(modifier: Modifier): Modifier {
        var result = modifier
        for (style in getStyles()){
            result = style.decorate(result)
        }
        return result
    }

    abstract fun getStyles():List<ComposeStyle>

}

open class ComposeStyleConfig: ComposeStyleGroup(){

    private val styles = mutableListOf<ComposeStyle>()

    override fun getStyles(): List<ComposeStyle> {
        if (styles.isNotEmpty()){
            return styles
        }
        //通过反射注入
        val javaClass = this.javaClass
        val fields = javaClass.declaredFields
        for (field in fields){
            if (field.type == ComposeStyle::class.java){
                field.isAccessible = true
                val style = field.get(this) as ComposeStyle
                styles.add(style)
            }
        }
        return styles
    }
}

