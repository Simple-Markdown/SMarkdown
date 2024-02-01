package indi.midreamsheep.app.markdown.model.main.file

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

interface TREFile {
    fun listSubFiles():List<TREFile>
    fun isDirectory():Boolean
    fun getName():String
    @Composable
    fun recall(isClicked: MutableState<Boolean>)
}