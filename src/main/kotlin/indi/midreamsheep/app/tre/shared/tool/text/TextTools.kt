package indi.midreamsheep.app.tre.shared.tool.text


/**
 * 找到指定字符串中的指定前缀和后缀的位置
 * 匹配为最大匹配，即找到最后一个匹配的位置
 * 例如传入的字符串为"**123***789**"，前缀为"**"，后缀为"**"，则返回的位置为(0,6)
 * @param text 指定字符串
 * @param prefix 前缀
 * @param suffix 后缀 若不传则默认为前缀
 * @param startPoint 开始查找的位置 默认为0
 * @return 返回前缀和后缀的位置，如果没有找到则返回(-1,-1)
 * */
fun findAffixPoint(
    text: String,
    prefix: String,
    suffix: String = prefix,
    startPoint: Int = 0
):Pair<Int,Int>{
    var pointer = startPoint
    val prefixArray = prefix.toCharArray()
    val suffixArray = suffix.toCharArray()
    if(text.length<prefixArray.size+suffixArray.size){
        return Pair(-1,-1)
    }
    while (pointer<text.length-prefixArray.size) {
        if (isStartWithInPoint(text,prefix,pointer)){
            break
        }
        pointer++
    }
    if (pointer==text.length-prefixArray.size) return Pair(-1,-1)
    val start = pointer
    pointer += prefixArray.size
    while (pointer<text.length-suffixArray.size) {
        if (isStartWithInPoint(text,suffix,pointer)){
            break
        }
        pointer++
    }
    //继续向外扩展
    while (pointer<text.length-suffixArray.size) {
        if (!isStartWithInPoint(text,suffix,pointer)){
            pointer--
            break
        }
        pointer++
    }
    return Pair(start,pointer)
}

/**
 * 判断指定字符串从某个位置开始是否以指定前缀开始
 * @param text 指定字符串
 * @param prefix 前缀
 * @param startPoint 开始查找的位置
 * @return 如果以指定前缀开始则返回true，否则返回false
 * */
fun isStartWithInPoint(
    text: String,
    prefix: String,
    startPoint: Int
):Boolean{
    if (text.length-startPoint<prefix.length) return false
    val prefixArray = prefix.toCharArray()
    for (i in prefixArray.indices) {
        if (text[startPoint+i]!=prefixArray[i]) return false
    }
    return true
}