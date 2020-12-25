package com.yy.mobile.emoji

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


/**
 * Created by 张宇 on 2019-07-23.
 * E-mail: zhangyu4@yy.com
 * YY: 909017428
 */
class AssertEmojiTest {

    @Test
    fun isNumber() {
        for (number in 0..9) {
            Assert.assertFalse(
                "number is $number",
                EmojiReader.isEmojiOfCharIndex(number.toString(), 0)
            )
        }
    }

    @Test
    fun isCharacter() {
        for (c in 'a'..'z') {
            Assert.assertFalse(
                "char is $c",
                EmojiReader.isEmojiOfCharIndex(c.toString(), 0)
            )
        }
        for (c in 'A'..'Z') {
            Assert.assertFalse(
                "char is $c",
                EmojiReader.isEmojiOfCharIndex(c.toString(), 0)
            )
        }
    }

    @Test
    fun isChinese() {
        val str =
            "孔雀东南飞，五里一徘徊。“十三能织素，十四学裁衣，十五弹箜篌，十六诵诗书。十七为君妇，心中常苦悲。" +
                    "君既为府吏，守节情不移，贱妾留空房，相见常日稀。鸡鸣入机织，夜夜不得息。三日断五匹，大人故嫌迟。" +
                    "非为织作迟，君家妇难为！妾不堪驱使，徒留无所施，便可白公姥，及时相遣归。”"
        for (idx in 0 until str.length) {
            Assert.assertFalse(
                "index of $idx is ${str[idx]}",
                EmojiReader.isEmojiOfCharIndex(str, idx)
            )
        }
    }

    @Test
    fun isJapanese() {
        val str = """ミカンとリンゴを買う北海道の冬は冷たくて厳しい買いたいが、金がない疲れたので、動けない雨が降る私は学生だ"""
        for (idx in str.indices) {
            Assert.assertFalse(
                "index of $idx is ${str[idx]}",
                EmojiReader.isEmojiOfCharIndex(str, idx)
            )
        }
    }

    @Test
    fun isPunctuation() {
        val enPunc = """`~!@#$%^&*()_+-=\|[]{};:'",<.>/?"""
        val chPunc = """～·！@#¥%……&*（）-——=+【「】」、|；：'"，《。》/？"""
        val punc = enPunc + chPunc
        for (idx in punc.indices) {
            Assert.assertFalse(
                "index of $idx is ${punc[idx]}",
                EmojiReader.isEmojiOfCharIndex(punc, idx)
            )
        }
    }

    @Test
    fun isSpecialIcon() {
        val icon = """œ∑´´†¥¨ˆˆπåß∂ƒ˙∆˚¬…æΩ≈ç√∫˜˜≤≥"""
        for (idx in icon.indices) {
            Assert.assertFalse(
                "index of $idx is ${icon[idx]}",
                EmojiReader.isEmojiOfCharIndex(icon, idx)
            )
        }
    }

    @Test
    fun isSpecialSymbol() {
        val emoji = intArrayOf(0x3030, 0x00A9, 0x00AE, 0x2122).encodeString()
        println(emoji)
        for (idx in emoji.indices) {
            Assert.assertTrue(
                "index of $idx is ${emoji[idx]}",
                EmojiReader.isEmojiOfCharIndex(emoji, idx)
            )
        }
    }

    @Test
    fun isWhiteSpace() {
        val str = "\n \t \r"
        for (idx in str.indices) {
            Assert.assertFalse(
                "index of $idx is ${str[idx]}",
                EmojiReader.isEmojiOfCharIndex(str, idx)
            )
        }
    }

    @Test
    fun isSingleEmoji() {
        val emoji = (0x1F600..0x1F6FF).toList().toIntArray().encodeString()
        println(emoji)
        for (idx in emoji.indices) {
            Assert.assertTrue(
                "index of $idx is ${emoji[idx]}",
                EmojiReader.isEmojiOfCharIndex(emoji, idx)
            )
        }
    }

    @Test
    fun isComposeEmoji() {
        val composeEmoji = listOf(
            "youngMan" to intArrayOf(0x1F471, 0x200D, 0x2642, 0xFE0F),
            "strongMan" to intArrayOf(0x1F468, 0x200D, 0x1F9B0),
            "oldMan" to intArrayOf(0x1F468, 0x200D, 0x1F9B3),
            "oldWoman" to intArrayOf(0x1F469, 0x200D, 0x1F9B3),
            "baldHead" to intArrayOf(0x1F469, 0x200D, 0x1F9B2),
            "handsupBoy" to intArrayOf(0x1F64B, 0x200D, 0x2642, 0xFE0F),
            "detectorMan" to intArrayOf(0x1F575, 0xFE0F, 0x200D, 0x2642, 0xFE0F),
            "detectorGirl" to intArrayOf(0x1F575, 0xFE0F, 0x200D, 0x2640, 0xFE0F),
            "playBasketBall" to intArrayOf(0x26F9, 0xFE0F, 0x200D, 0x2642, 0xFE0F),
            "boy_heart_girl" to intArrayOf(
                0x1F469,
                0x200D,
                0x2764,
                0xFE0F,
                0x200D,
                0x1F48B,
                0x200D,
                0x1F468
            ),
            "four_man_family" to intArrayOf(
                0x1F468,
                0x200D,
                0x1F469,
                0x200D,
                0x1F467,
                0x200D,
                0x1F466
            )
        )

        val allEmoji = StringBuilder()
        for ((_, codePoint) in composeEmoji) {
            allEmoji.append(String(codePoint, 0, codePoint.size))
        }

        for (idx in composeEmoji.indices) {
            Assert.assertTrue(
                "emoji of name ${composeEmoji[idx].first} is error.",
                EmojiReader.isEmojiOfVisionIndex(allEmoji, idx)
            )
        }
        println(allEmoji)
    }

    @Test
    fun isAnimalEmoji() {
        val emoji = (0x1F400..0x1F439).toList().toIntArray().encodeString()
        println(emoji)
        for (idx in emoji.indices) {
            Assert.assertTrue(
                "index of $idx is ${emoji[idx]}",
                EmojiReader.isEmojiOfCharIndex(emoji, idx)
            )
        }
    }

    @Test
    fun isPlantEmoji() {
        val emoji = (0x1F330..0x1F346).toList().toIntArray().encodeString()
        println(emoji)
        for (idx in emoji.indices) {
            Assert.assertTrue(
                "index of $idx is ${emoji[idx]}",
                EmojiReader.isEmojiOfCharIndex(emoji, idx)
            )
        }
    }

    @Test
    fun isFoodEmoji() {
        val emoji = (0x1F347..0x1F37F).toList().toIntArray().encodeString()
        println(emoji)
        for (idx in emoji.indices) {
            Assert.assertTrue(
                "index of $idx is ${emoji[idx]}",
                EmojiReader.isEmojiOfCharIndex(emoji, idx)
            )
        }
    }

    @Test
    fun isMoomEmoji() {
        val emoji = (0x1F311..0x1F31F).toList().toIntArray().encodeString()
        println(emoji)
        for (idx in emoji.indices) {
            Assert.assertTrue(
                "index of $idx is ${emoji[idx]}",
                EmojiReader.isEmojiOfCharIndex(emoji, idx)
            )
        }
    }

    @Test
    fun isKeyCapEmoji() {
        val allEmoji = StringBuilder()
        (0x23..0x39).forEach { start ->
            val emoji = intArrayOf(start, 0xFE0F, 0x20E3).encodeString()
            allEmoji.append(emoji)
        }

        println(allEmoji)
        for (idx in allEmoji.indices) {
            Assert.assertTrue(
                "index of $idx is ${allEmoji[idx]}",
                EmojiReader.isEmojiOfCharIndex(allEmoji, idx)
            )
        }
    }

    @Test
    fun isGeometricEmoji() {
        val codePoint = intArrayOf(
            0x1F534,
            0x1F535,
            0x26AB,
            0x26AA,
            0x2B1B,
            0x2B1C,
            0x25FC,
            0x25FB,
            0x25AB,
            0x1F536,
            0x1F537,
            0x1F4A0,
            0x1F532
        )
        val emoji = codePoint.encodeString()
        println(emoji)
        for (idx in emoji.indices) {
            Assert.assertTrue(
                "index of $idx is ${emoji[idx]}",
                EmojiReader.isEmojiOfCharIndex(emoji, idx)
            )
        }
    }

    @Test
    fun isFlagEmoji() {
        val flag = listOf(
            intArrayOf(0x1F3C1),
            intArrayOf(0x1F6A9),
            intArrayOf(0x1F38C),
            intArrayOf(0x1F3F4),
            intArrayOf(0x1F3F3),
            intArrayOf(0x1F3F3, 0xFE0F, 0x200D, 0x1F308),
            intArrayOf(0x1F3F4, 0x200D, 0x2620, 0xFE0F),
            intArrayOf(0x1F1E6, 0x1F1E8),
            intArrayOf(0x1F1E6, 0x1F1E9),
            intArrayOf(0x1F1E6, 0x1F1EA),
            intArrayOf(0x1F1E6, 0x1F1EB),
            intArrayOf(0x1F1E7, 0x1F1EA),
            intArrayOf(0x1F1E7, 0x1F1F7),
            intArrayOf(0x1F1E8, 0x1F1F1),
            intArrayOf(0x1F1E9, 0x1F1EC),
            intArrayOf(0x1F1EA, 0x1F1EA),
            intArrayOf(0x1F1EB, 0x1F1F0),
            intArrayOf(0x1F1EC, 0x1F1F6),
            intArrayOf(0x1F1EF, 0x1F1F2),
            intArrayOf(0x1F1F1, 0x1F1FA),
            intArrayOf(0x1F1F2, 0x1F1FA),
            intArrayOf(0x1F1F5, 0x1F1EA),
            intArrayOf(0x1F1FF, 0x1F1FC),
            intArrayOf(0x1F3F4, 0xE0067, 0xE0062, 0xE0065, 0xE006E, 0xE0067, 0xE007F),
            intArrayOf(0x1F3F4, 0xE0067, 0xE0062, 0xE0073, 0xE0063, 0xE0074, 0xE007F),
            intArrayOf(0x1F3F4, 0xE0067, 0xE0062, 0xE0077, 0xE006C, 0xE0073, 0xE007F)
        )
        flag.forEach { codePoint ->
            val emoji = codePoint.encodeString()
            print(emoji)
            for (idx in emoji.indices) {
                Assert.assertTrue(
                    "index of $idx is ${emoji[idx]}",
                    EmojiReader.isEmojiOfCharIndex(emoji, idx)
                )
            }
        }
        println()
    }

    /**
     * https://www.unicode.org/emoji/charts-13.1/emoji-released.html
     */
    @Test
    fun supportEmojiVersion13() {
        assertEmoji(0x2764, 0xFE0F, 0x200D, 0x1FA79)
        assertEmoji(0x2764, 0xFE0F, 0x200D, 0x1F525)
        assertEmoji(0x1F9D4)
        assertEmoji(0x1F469, 0x200D, 0x2764, 0xFE0F, 0x200D, 0x1F48B, 0x200D, 0x1F468)
    }

    private fun assertEmoji(vararg codePoint: Int) {
        val emoji = codePoint.encodeString()
        assertTrue(EmojiReader.isEmojiOfCharIndex(emoji, 0))
        assertEquals(1, EmojiReader.getTextLength(emoji))
        println(emoji + ": " + EmojiReader.analyzeText(emoji))
    }
}