package indi.midreamsheep.app.markdown.editor.manager

class Test :MarkdownFileManager {
    private val markdownStateManager = TestMarkdownStateManager()
    override fun read(): Pair<Boolean, String> {
        TODO("Not yet implemented")
    }

    override fun store(): Pair<Boolean, String> {
        TODO("Not yet implemented")
    }

    override fun isRead(): Boolean {
        return true
    }

    override fun getStateManager(): MarkdownStateManager {
        return markdownStateManager
    }
}