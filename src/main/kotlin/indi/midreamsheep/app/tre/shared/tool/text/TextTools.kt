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
    // 指针
    var pointer = startPoint
    // 前缀数组
    val prefixArray = prefix.toCharArray()
    // 后缀数组
    val suffixArray = suffix.toCharArray()
    // 是否查询到第一个
    var isThrough = false
    // 若整个数组少于前后缀长度则返回-1，-1
    if(text.length - startPoint<prefixArray.size+suffixArray.size) return Pair(-1,-1)
    //找到第一个满足前缀的位置
    while (pointer<text.length-suffixArray.size) {
        if (isStartWithInPoint(text,prefix,pointer)){
            isThrough = true
            break
        }
        pointer++
    }
    //若不存在前缀则返回-1
    if (!isThrough) return Pair(-1,-1)

    isThrough = false
    //继续前进到前缀失效处
    while (pointer<text.length-suffixArray.size) {
        if (!isStartWithInPoint(text,prefix,pointer)){
            pointer--
            break
        }
        pointer++
    }
    val start = pointer
    //前进到开始识别后缀处
    pointer += prefixArray.size
    while (pointer<=text.length-suffixArray.size) {
        if (isStartWithInPoint(text,suffix,pointer)){
            isThrough = true
            break
        }
        pointer++
    }
    if (!isThrough) return Pair(-1,-1)
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
    for ((index, c) in prefixArray.withIndex()) {
        if (text[startPoint+index]!=c) return false
    }
    return true
}