package indi.midreamsheep.app.markdown.editor.manager

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class TestMarkdownStateManager:MarkdownStateManager {

    private var nowFocus:Int = 0

    private val markdownLineStateList = mutableStateListOf<MarkdownLineState>(
        MarkdownLineState()
    )

    override fun getMarkdownLineStateList(): SnapshotStateList<MarkdownLineState> {
        return markdownLineStateList
    }

    override fun getNowFocusedLine(): Int {
        return nowFocus
    }

    override fun setNowFocusedLine(line: Int) {
        nowFocus = line
    }
}