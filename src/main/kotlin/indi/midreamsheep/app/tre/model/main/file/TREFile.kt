package indi.midreamsheep.app.tre.model.main.file

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

interface TREFile {
    fun listSubFiles():List<TREFile>
    fun isDirectory():Boolean
    fun getName():String
    @Composable
    fun recall(isClicked: MutableState<Boolean>)
}