package com.yy.mobile.emoji

/**
 * @author YvesCheung
 * 2020/11/6
 */
internal object StringFactory {

    fun newStringFromCodePoints(codePoints: IntArray?, offset: Int, count: Int): String {
        if (codePoints == null) {
            throw NullPointerException("codePoints == null")
        }
        if (offset or count < 0 || count > codePoints.size - offset) {
            throw IndexOutOfBoundsException()
        }
        val value = CharArray(count * 2)
        val end = offset + count
        var length = 0
        for (i in offset until end) {
            length += Character.toChars(codePoints[i], value, length)
        }
        return String(value, 0, length)
    }
}

@Suppress("FunctionName")
fun String(codePoints: IntArray, offset: Int, length: Int): String {
    return StringFactory.newStringFromCodePoints(codePoints, offset, length)
}