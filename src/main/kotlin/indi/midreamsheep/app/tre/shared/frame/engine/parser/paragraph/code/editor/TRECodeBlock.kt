package indi.midreamsheep.app.tre.shared.frame.engine.parser.paragraph.code.editor

/*
class TRECodeBlock(treBlockManager: TREBlockManager, type:String): TREBlockAbstract(treBlockManager) {

    val content = mutableStateOf(TextFieldValue(""))
    val codeType = mutableStateOf(type.replace("```",""))
    val focus = mutableStateOf(false)
    val focusRequester = FocusRequester()

    override fun focus() {
        focus.value = true
        getBlockManager().setCurrentBlock(this)
    }

    override fun releaseFocus() {
         focus.value = false
        getBlockManager().setCurrentBlock(null)
    }

    override fun getDisplay(): Display {
        return Display{
            {
                codeInputTextField(this)
            }
        }
    }

    override fun getContent(): String {
        return "```${codeType.value}\n${content.value.text}\n```\n"
    }

    override fun whenInsert() {}

    override fun whenRemove() {}

}*/
