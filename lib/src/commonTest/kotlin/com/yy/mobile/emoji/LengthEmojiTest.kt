package com.yy.mobile.emoji

import kotlin.test.Test

/**
 * @author YvesCheung
 * 2019/7/23
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
        intArrayOf(0x1F468, 0x200D, 0x1F469, 0x200D, 0x1F467, 0x200D, 0x1F466),
        intArrayOf(0x1F636, 0x200D, 0x1F32B, 0xFE0F) //face in clouds
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

    @Test
    fun singleEmojiTest() {
        val str = singleEmojiSample.encodeString()
        println(str)
        Assert.assertEquals(EmojiReader.getTextLength(str), singleEmojiSample.size)
    }

    @Test
    fun flagEmojiTest() {
        val str = flagEmojiSample.encodeString()
        println(str)
        Assert.assertEquals(EmojiReader.getTextLength(str), flagEmojiSample.size)
    }

    @Test
    fun composeEmojiTest() {
        val str = composeEmojiSample.encodeString()
        println(str)
        Assert.assertEquals(EmojiReader.getTextLength(str), composeEmojiSample.size)
    }

    @Test
    fun emojiTest() {
        val emoji = intArrayOf(0x1F477, 0x2640, 0xFE0F)
        println(emoji.encodeString())
    }

    @Test
    fun diversityEmojiTest() {
        val emoji = "\uD83D\uDC81\uD83C\uDFFB" +
                "\uD83D\uDC81\uD83C\uDFFC" +
                "\uD83D\uDC81\uD83C\uDFFD" +
                "\uD83D\uDC81\uD83C\uDFFE" +
                "\uD83D\uDC81\uD83C\uDFFF" +
                "\uD83D\uDC81"
        val length = EmojiReader.getTextLength(emoji)

        println(emoji)
        println(EmojiReader.transToUnicode(emoji))
        println(length)

        Assert.assertEquals(length, 6)
    }

    @Test
    fun emojiWithColorTest() {
        val cat = 0x1F408
        val blackCat = intArrayOf(cat, 0x200D, 0x2B1B).encodeString()
        println(blackCat)
        val orangeCat = intArrayOf(cat, 0x200D, 0x1F7E7).encodeString()
        println(orangeCat)

        Assert.assertEquals(EmojiReader.getTextLength(blackCat), 1)
        Assert.assertEquals(EmojiReader.getTextLength(orangeCat), 1)
    }

    @Test
    fun emojiGlyphFacingDirection() {
        val faceToLeft = intArrayOf(0x1F3C3, 0x200D, 0x2B05, 0xFE0F).encodeString()
        println(faceToLeft)
        val faceToRight = intArrayOf(0x1F3C3, 0x200D, 0x27A1, 0xFE0F).encodeString()
        println(faceToRight)

        Assert.assertEquals(EmojiReader.getTextLength(faceToLeft), 1)
        Assert.assertEquals(EmojiReader.getTextLength(faceToRight), 1)
    }
}