package indi.midreamsheep.app.tre.shared.frame.engine

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import indi.midreamsheep.app.tre.shared.frame.engine.context.TREEditorContext

val editorContext = compositionLocalOf<TREEditorContext>{ error("the editor context does not init properly") }

@Composable
fun getEditorContextComposition() = editorContext

@Composable
fun getEditorContext(): TREEditorContext = editorContext.current