package com.yy.mobile.emoji

import kotlin.test.Test

/**
 * Created by 张宇 on 2019-07-23.
 * E-mail: zhangyu4@yy.com
 * YY: 909017428
 */
class LengthEmojiTest {

    private val singleEmojiSample = (0x1F330..0x1F37F).map { intArrayOf(it) }

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

    private fun List<IntArray>.encodeString(): String {
        return this.joinToString(separator = "") { codePoint ->
            String(codePoint, 0, codePoint.size)
        }
    }

    @Test
    fun singleEmojiTest() {

        val str = singleEmojiSample.encodeString()

        Assert.assertEquals(EmojiReader.getTextLength(str), singleEmojiSample.size)
    }

    @Test
    fun flagEmojiTest() {

        val str = flagEmojiSample.encodeString()

        Assert.assertEquals(EmojiReader.getTextLength(str), flagEmojiSample.size)
    }

    @Test
    fun composeEmojiTest() {

        val str = composeEmojiSample.encodeString()

        Assert.assertEquals(EmojiReader.getTextLength(str), composeEmojiSample.size)
    }

    @Test
    fun emojiTest() {

        val emoji = intArrayOf(0x1F477,0x2640,0xFE0F)
        val str = String(emoji,0,emoji.size)
        println(str)
    }
}