package indi.midreamsheep.app.tre.desktop.page.editor.ui.editors.render.code

/*
@Composable
fun codeInputTextField(
    codeLine: TRECodeBlock,
) {
    Column {
        BasicTextField(
            value = codeLine.content.value,
            onValueChange = { newValue ->
                val count = newValue.text.count { it == '\t' }
                val selection = newValue.selection
                codeLine.content.value = newValue.copy(
                    text = newValue.text.replace("\t", "    "),
                    selection = TextRange(
                        start = selection.start + count * 3,
                        end = selection.end + count * 3
                    )
                )
            },
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(codeLine.focusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        codeLine.focus()
                    } else {
                        codeLine.releaseFocus()
                    }
                }
                .background(Color(0xFFF0F0F1))
                .padding(5.dp),
            keyboardActions = KeyboardActions(onDone = {}),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            visualTransformation = { _ ->
                TransformedText(
                    text = build(codeLine.content.value.text),
                    offsetMapping = OffsetMapping.Identity,
                )//compo
            },
        ){
            Box{
                if (codeLine.content.value.text.isEmpty()) {
                    Text(
                        text = " ",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                it()
            }
        }
    }
    LaunchedEffect(codeLine.focus.value) {
        if (codeLine.focus.value) {
            codeLine.focusRequester.requestFocus()
        }
    }
}

private fun build(
    content:String
): AnnotatedString{
    val builder = AnnotatedString.Builder(content)

    val keywords = mapOf(
        "\\bpublic\\b" to Color(0xffb65e34),
        "\\bclass\\b" to Color(0xffb65e34),
        "\\bvoid\\b" to Color(0xffb65e34),
        "\\bstatic\\b" to Color(0xffb65e34),
        "\\bint\\b" to Color(0xffb65e34),
        "\\bif\\b" to Color(0xffb65e34),
        "\\belse\\b" to Color(0xffb65e34),
        "\\bfor\\b" to Color(0xffb65e34),
        "\\bwhile\\b" to Color(0xffb65e34),
        "\"(.*?)\"" to Color(0xff469472),
        "'(.*?)'" to Color(0xff469472),
        "\\b\\w+(?=\\()" to Color(0xff2958ba),
    )

    for ((keyword, color) in keywords) {
        val pattern = keyword.toRegex()
        pattern.findAll(content).forEach { matchResult ->
            builder.addStyle(
                style = SpanStyle(color = color),
                start = matchResult.range.first,
                end = matchResult.range.last + 1
            )
        }
    }

    return builder.toAnnotatedString()
}*/
