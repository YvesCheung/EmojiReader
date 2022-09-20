package com.yy.mobile.emoji


/**
 * @author YvesCheung
 * 2019/7/11
 *
 * https://www.unicode.org/reports/tr51/tr51-13.html#Proposals
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
object EmojiReader {

    fun getTextLength(str: CharSequence): Int {
        val sm = StateMachine()
        sm.read(str)
        return sm.getCurrentCharSize()
    }

    fun analyzeText(str: CharSequence): List<Node> {
        val sm = StateMachine()
        sm.read(str)
        return sm.getCharList().map { data ->
            val length = data.codePoint.fold(0) { sum, code ->
                sum + Character.charCount(code)
            }
            Node(data.startIndex, length, data.isEmoji, data.codePoint.toList())
        }
    }

    /**
     * 判断字符串中指定位置的字符是否是 `emoji` 表情。
     * 注意这里的索引是可见的索引数，而不是字符的索引数。
     * 一个 `emoji` 可能由多个 `unicode` 码点组成，那么它是一个可见的长度，但却是多个字符长度。
     *
     * @param str 可能包含 `emoji` 的字符串
     * @param idx 视觉上的索引数，即按照一个 `emoji` 长度为一来计算的索引
     *
     * @return 是否 `emoji` 表情。
     */
    fun isEmojiOfVisionIndex(str: CharSequence, idx: Int): Boolean =
        isEmojiOfVisionIndex(analyzeText(str), idx)

    /**
     * 判断字符串中指定位置的字符是否是 `emoji` 表情。
     * 注意这里的索引是可见的索引数，而不是字符的索引数。
     * 一个 `emoji` 可能由多个 `unicode` 码点组成，那么它是一个可见的长度，但却是多个字符长度。
     *
     * @param nodeList 通过 [analyzeText] 得出的结果。
     * @param idx 视觉上的索引数，即按照一个 `emoji` 长度为一来计算的索引
     *
     * @return 是否 `emoji` 表情。
     */
    fun isEmojiOfVisionIndex(nodeList: List<Node>, idx: Int): Boolean {
        return nodeList[idx].isEmoji
    }

    /**
     * 判断字符串中指定位置的字符是否是 `emoji` 表情。
     * 注意这里的索引是字符的索引数。
     * 一个 `emoji` 可能由多个 `unicode` 码点组成，那么它是一个可见的长度，但却是多个字符长度。
     *
     * @param str 可能包含 `emoji` 的字符串。
     * @param idx 字符的索引数，即 idx 范围为 [0, String::length] 。
     *
     * @return 是否 `emoji` 表情。
     */
    fun isEmojiOfCharIndex(str: CharSequence, idx: Int): Boolean =
        isEmojiOfCharIndex(analyzeText(str), idx)

    /**
     * 判断字符串中指定位置的字符是否是 `emoji` 表情。
     * 注意这里的索引是字符的索引数。
     * 一个 `emoji` 可能由多个 `unicode` 码点组成，那么它是一个可见的长度，但却是多个字符长度。
     *
     * @param nodeList 通过 [analyzeText] 得出的结果。
     * @param idx 字符的索引数，即 idx 范围为 [0, String::length]。
     *
     * @return 是否 `emoji` 表情。
     */
    fun isEmojiOfCharIndex(nodeList: List<Node>, idx: Int): Boolean {
        val visionIdx = nodeList.binarySearch { node ->
            when {
                idx < node.startIndex -> 1
                idx >= node.startIndex + node.length -> -1
                else -> 0
            }
        }
        if (visionIdx < 0) {
            return false
        }
        return isEmojiOfVisionIndex(nodeList, visionIdx)
    }

    /**
     * 裁剪包含 `emoji` 表情的字符串，并确保 `emoji` 不会被砍成两半。
     *
     * Returns a string that is a substring of this string
     * and ensure that `Emoji' is not cut in half.
     */
    fun subSequence(str: CharSequence, end: Int): CharSequence = subSequence(str, 0, end)

    /**
     * 裁剪包含 `emoji` 表情的字符串，并确保 `emoji` 不会被砍成两半。
     *
     * Returns a string that is a substring of this string
     * and ensure that `Emoji' is not cut in half.
     */
    fun subSequence(str: CharSequence, start: Int, end: Int): CharSequence {
        if (start < 0 || end > str.length) {
            throw IndexOutOfBoundsException(
                "The index should be in range [0,${str.length}]," +
                    "but actually start = $start and end = $end."
            )
        }
        if (start > end) {
            throw IndexOutOfBoundsException(
                "The start index should be not bigger than end," +
                    "but actually start = $start and end = $end."
            )
        }
        if (start == end) {
            return ""
        }
        val sm = StateMachine()
        sm.read(str, start + end)
        val charList = sm.getCharList()

        val startIdx = charList.getOrNull(start)?.startIndex
            ?: return ""

        val endIdx = charList.getOrNull(end - 1)?.let {
            it.startIndex + it.codePoint.fold(0) { sum, cp -> sum + Character.charCount(cp) }
        }
        return if (endIdx == null) {
            str.subSequence(startIdx, str.length)
        } else {
            str.subSequence(startIdx, endIdx)
        }
    }

    fun transToUnicode(str: CharSequence): List<String> {
        val result = mutableListOf<String>()
        str.forEachCodePoint { codePoint ->
            result.add("U+" + Integer.toHexString(codePoint))
        }
        return result
    }

    private inline fun CharSequence.forEachCodePoint(action: (Int) -> Unit) {
        var i = 0
        while (i < this.length) {
            val codePoint = Character.codePointAt(this, i)
            action(codePoint)
            i += Character.charCount(codePoint)
        }
    }

    /**
     * EBNF of emoji:
     *
     * possible_emoji :=
     * flag_sequence
     *      | zwj_element (\x{200D} zwj_element)+
     *
     * flag_sequence :=
     *      \p{RI} \p{RI}
     *
     * zwj_element :=
     *      \p{Emoji} emoji_modification?
     *
     * emoji_modification :=
     *      \p{EMod}
     *      | \x{FE0F} \x{20E3}?
     *      | tag_modifier
     *
     * tag_modifier :=
     *      [\x{E0020}-\x{E007E}]+ \x{E007F}
     */
    private class StateMachine {

        companion object {

            /**
             * possible_emoji :=  zwj_element (\x{200D} zwj_element)+
             *
             * 200D 连接两个emoji元素成为一个新的emoji
             */
            const val Joiner = 0x200D

            /**
             * emoji_modification :=
             * \p{EMD}
             * | (\x{FE0F} | \p{Me}) \p{Me}*
             * | tag_modifier
             *
             * E.g 0x23 0xFE0F 0x20E3
             */
            const val ModifierBlack = 0xFE0E
            const val ModifierColorFul = 0xFE0F
            const val ModifierKeyCap = 0x20E3

            /**
             * tag_modifier := [\x{E0020}-\x{E007E}]+ \x{E007F}
             */
            val ModifierTagRange = 0xE0020..0xE007F

            /**
             * diversity
             */
            val ModifierSkinTone = setOf(
                0x1F3FB, //light skin tone
                0x1F3FC, //medium-light skin tone
                0x1F3FD, //medium skin tone
                0x1F3FE, //medium-dark skin tone
                0x1F3FF //dark skin tone
            )

            /**
             * 状态机的状态
             *
             * state in state machine
             */
            const val STATE_DEFAULT = 0x0
            const val STATE_EMOJI = 0x1
            const val STATE_PRE_EMOJI = 0x10
            const val STATE_NATIONAL_FLAG = STATE_EMOJI or 0x100
            const val STATE_EMOJI_MODIFIER = STATE_EMOJI or 0x1000
            const val STATE_EMOJI_JOIN = 0x10000
        }

        private val emojiModifier = setOf(
            ModifierBlack,
            ModifierColorFul,
            ModifierKeyCap
        ) + ModifierTagRange + ModifierSkinTone

        private val charUnitList = mutableListOf<InnerNode>()

        private var currentIndex = 0

        private var currentCodePoint = 0x0

        private var currentChar = InnerNode(0)

        private var currentState = STATE_DEFAULT

        /**
         * 结束一个完整的字符或者一个完整的emoji
         */
        private fun endChar() {
            currentState = STATE_DEFAULT
            if (currentChar.codePoint.isNotEmpty()) {
                charUnitList.add(currentChar)
                currentChar = InnerNode(currentIndex)
            }
        }

        private fun assertEmoji() {
            currentChar.isEmoji = true
        }

        private fun moveToNext() {
            currentChar.codePoint.add(currentCodePoint)
            currentIndex += Character.charCount(currentCodePoint)
        }

        private fun moveToPrev() {
            val lastCodePoint = currentChar.codePoint.removeAt(currentChar.codePoint.lastIndex)
            currentIndex -= Character.charCount(lastCodePoint)
        }

        fun read(str: CharSequence, end: Int = str.length) {

            while (currentIndex < str.length) {

                currentCodePoint = Character.codePointAt(str, currentIndex)

                when {
                    currentState == STATE_EMOJI_JOIN -> when {
                        isEmojiCodePoint(currentCodePoint) -> {
                            /**
                             * emoji + emoji
                             * +号后面是emoji，符合期望
                             */
                            currentState = STATE_EMOJI
                            moveToNext()
                        }
                        else -> {
                            /**
                             * emoji + !emoji
                             * 因为 + 后面没有跟另一个emoji，所以回塑到 + 这个字符
                             * + 不再代表emoji的连=连接符
                             */
                            moveToPrev()
                            endChar()
                        }
                    }
                    currentState == STATE_NATIONAL_FLAG -> when {
                        isRegionalIndicator(currentCodePoint) -> {
                            /**
                             * flag_sequence := \p{RI} \p{RI}
                             *
                             * 两个国家区域，完成一个国旗emoji，符合期望
                             */
                            moveToNext()
                            assertEmoji()
                            endChar()
                        }
                        else -> {
                            /**
                             * 没达到两个国家区域，但前面的也是emoji
                             *
                             * 结束前面一个国家区域字符，并且在下一次遍历处理当前字符
                             */
                            assertEmoji()
                            endChar()
                        }
                    }
                    currentState == STATE_PRE_EMOJI -> when {
                        emojiModifier.contains(currentCodePoint) -> {
                            /**
                             * maybeEmoji Modifier*
                             *
                             * emoji 后面可以跟多个 Modifier
                             */
                            currentState = STATE_EMOJI_MODIFIER
                            moveToNext()
                        }
                        else -> {
                            /**
                             * 结束前面一个字符，并且在下一次遍历处理当前字符
                             */
                            endChar()
                        }
                    }
                    /**
                     * 当前是 Emoji 状态或者 Modifier 状态，
                     * 因为在 EBNF 里面 Emoji 和 Modifier 后面可以跟的码点是一样的，所以放在一起处理
                     */
                    currentState and STATE_EMOJI != 0 -> when {
                        Joiner == currentCodePoint -> {
                            /**
                             * emoji + emoji
                             *
                             * 准备连接下一个emoji
                             */
                            currentState = STATE_EMOJI_JOIN
                            moveToNext()
                        }
                        emojiModifier.contains(currentCodePoint) -> {
                            /**
                             * emoji Modifier*
                             *
                             * emoji 或 Modifier 后面可以跟多个 Modifier
                             */
                            currentState = STATE_EMOJI_MODIFIER
                            moveToNext()
                        }
                        else -> {
                            /**
                             * 结束前面一个Emoji，并且在下一次遍历处理当前字符
                             */
                            assertEmoji()
                            endChar()
                        }
                    }
                    else -> when {
                        /**
                         * flag_sequence := \p{RI} \p{RI}
                         *
                         * 遇到第一个国家区域，等待下一个国家区域可以合并成一个国旗emoji
                         */
                        isRegionalIndicator(currentCodePoint) -> {
                            currentState = STATE_NATIONAL_FLAG
                            moveToNext()
                        }
                        /**
                         * 有可能是emoji码点，由下一个码点是否是修饰符来决定
                         */
                        maybeEmojiCodePoint(currentCodePoint) -> {
                            currentState = STATE_PRE_EMOJI
                            moveToNext()
                        }
                        /**
                         * emoji码点，等待下一个 Join 或者 Modifier
                         */
                        isEmojiCodePoint(currentCodePoint) -> {
                            currentState = STATE_EMOJI
                            moveToNext()
                        }
                        /**
                         * 普通字符
                         */
                        else -> {
                            moveToNext()
                            endChar()
                        }
                    }
                }

                if (getCurrentCharSize() >= end) {
                    break
                }
            }

            if (currentState != STATE_DEFAULT) {
                if (currentState and STATE_EMOJI != 0) {
                    assertEmoji()
                }
                endChar()
            }
        }

        fun getCurrentIndex(): Int = currentIndex

        fun getCurrentCharSize(): Int = charUnitList.size

        fun getCharList(): List<InnerNode> = charUnitList

        /**
         *
         * \p{Emoji}
         *
         * zwj_element := \p{Emoji} emoji_modification?
         *
         *
         * Emoji的码点。这个方法不一定靠谱，通过unicode v12规范 emoji表观察归纳所得。
         * 随着unicode版本更新，这个codePoint的范围可能会增大。
         *
         * Basic Emoji's code point. This method is not necessarily reliable.
         * It is observed and summarized by the full Emoji table of the Unicode V12 specification.
         *
         * see [full-emoji-list](https://unicode.org/emoji/charts-12.0/full-emoji-list.html).
         */
        private fun isEmojiCodePoint(codePoint: Int) =
            (codePoint in 0x1F200..0x1FFFF) ||
                (codePoint in 0x231A..0x23FF) ||
                (codePoint in 0x2460..0x24FF) || //带圈或括号的字母数字
                (codePoint in 0x2500..0x2FFF) || //制表符/方块元素/几何图形/杂项/印刷/追加箭头/表意文字
                (codePoint in 0x3200..0x32FF) || //带圈中日韩字母月份
                isSpecialSymbol(codePoint)

        /**
         * very special case
         */
        private fun isSpecialSymbol(codePoint: Int) =
            codePoint == 0x3030 || //wavy dash
                codePoint == 0x00A9 || //copyright
                codePoint == 0x00AE || //registered
                codePoint == 0x2122 //trade mark

        /**
         * 不是独立emoji 要和修饰符一起用
         * 主要是方框数字，CodePoint = \p{Number} ModifierKeyCap
         * 样例： 0x39 0x20E3
         *
         * Not a stand-alone Emoji which should be used with modifiers,
         * CodePoint = \p{Number} ModifierKeyCap
         * E.g 0x39 0x20E3
         */
        private fun maybeEmojiCodePoint(codePoint: Int) =
            codePoint in 0x0..0x39 ||
                codePoint in 0x2190..0x21FF  //箭头，加上modifier会变成箭头emoji

        /**
         * Regional_Indicator
         *
         * emoji_flag_sequence := regional_indicator regional_indicator
         *
         * 国家区域标识，两个标识会变成一个国旗emoji。
         * National regional indicator, two indicator will become a national flag Emoji.
         */
        private fun isRegionalIndicator(codePoint: Int) = codePoint in 0x1F000..0x1F1FF
    }

    /**
     * inner data struct
     */
    private data class InnerNode(
        val startIndex: Int,
        var isEmoji: Boolean = false,
        val codePoint: MutableList<Int> = mutableListOf()
    )

    /**
     * public data struct
     */
    data class Node(
        val startIndex: Int,
        val length: Int,
        val isEmoji: Boolean,
        val codePoint: List<Int>
    ) {

        override fun toString(): String {
            return "Node(startIndex=$startIndex, " +
                "length=$length, " +
                "isEmoji=$isEmoji, " +
                "codePoint=${codePoint.joinToString { Integer.toHexString(it) }})"
        }
    }
}