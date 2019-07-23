package com.yy.mobile.emoji

import org.junit.Test

/**
 * Created by 张宇 on 2019-07-23.
 * E-mail: zhangyu4@yy.com
 * YY: 909017428
 */
class SubstringOfEmojiTest {

    private val singleEmojiSample = (0x1F600..0x1F8FF).map { intArrayOf(it) }

    private val composeEmojiSample = listOf(
        intArrayOf(0x1F471, 0x200D, 0x2642, 0xFE0F),
        intArrayOf(0x1F468, 0x200D, 0x1F9B0),
        intArrayOf(0x1F468, 0x200D, 0x1F9B3),
        intArrayOf(0x1F469, 0x200D, 0x1F9B3),
        intArrayOf(0x1F469, 0x200D, 0x1F9B2),
        intArrayOf(0x1F64B, 0x200D, 0x2642, 0xFE0F),
        intArrayOf(0x1F575, 0xFE0F, 0x200D, 0x2642, 0xFE0F),
        intArrayOf(0x1F575, 0xFE0F, 0x200D, 0x2640, 0xFE0F),
        intArrayOf(0x26F9, 0xFE0F, 0x200D, 0x2642, 0xFE0F),
        intArrayOf(0x1F469, 0x200D, 0x2764, 0xFE0F, 0x200D, 0x1F48B, 0x200D, 0x1F468),
        intArrayOf(0x1F468, 0x200D, 0x1F469, 0x200D, 0x1F467, 0x200D, 0x1F466)
    )

    private val flagEmojiSample = listOf(
        intArrayOf(0x1F3C1),
        intArrayOf(0x1F6A9),
        intArrayOf(0x1F3F3, 0xFE0F, 0x200D, 0x1F308),
        intArrayOf(0x1F1E8, 0x1F1F1),
        intArrayOf(0x1F1E9, 0x1F1EC),
        intArrayOf(0x1F1EA, 0x1F1EA),
        intArrayOf(0x1F3F4, 0xE0067, 0xE0062, 0xE0065, 0xE006E, 0xE0067, 0xE007F),
        intArrayOf(0x1F3F4, 0xE0067, 0xE0062, 0xE0077, 0xE006C, 0xE0073, 0xE007F)
    )

    private val emoji = singleEmojiSample + flagEmojiSample + composeEmojiSample

    @Test
    fun subStringWithSingleEmojiTest() {
        var i = 0
        val str = ('A'..'Z').joinToString(separator = "") { c ->
            val codePoint = singleEmojiSample[i++ % singleEmojiSample.size]
            c + String(codePoint, 0, codePoint.size)
        }

        println(str)

        println(EmojiReader.subSequence(str, 0, str.length))

        println(EmojiReader.subSequence(str, 3))

        println(EmojiReader.subSequence(str, 6))

        println(EmojiReader.subSequence(str, 10))

        println(EmojiReader.subSequence(str, 26))

        println(EmojiReader.subSequence(str, 0, 26))

        println(EmojiReader.subSequence(str, 1, 2))

        println(EmojiReader.subSequence(str, 3, 3))

        println(EmojiReader.subSequence(str, 10, 15))
    }

    @Test
    fun subStringWithAllEmoji() {
        val str = emoji.joinToString(separator = "") { codePoint ->
            String(codePoint, 0, codePoint.size) + 'A'
        }

        println(str)

        println(EmojiReader.subSequence(str, 0, str.length))

        println(EmojiReader.subSequence(str, 3))

        println(EmojiReader.subSequence(str, 6))

        println(EmojiReader.subSequence(str, 10))

        println(EmojiReader.subSequence(str, 100))

        println(EmojiReader.subSequence(str, 0, 26))

        println(EmojiReader.subSequence(str, 20, 22))

        println(EmojiReader.subSequence(str, 40, 56))

        println(EmojiReader.subSequence(str, 80, 100))
    }
}