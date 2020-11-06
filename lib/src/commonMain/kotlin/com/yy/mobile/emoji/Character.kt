package com.yy.mobile.emoji

/**
 * @author YvesCheung
 * 2020/11/6
 */
@Suppress("MemberVisibilityCanBePrivate")
internal object Character {

    /**
     * The maximum value of a
     * [Unicode code point](http://www.unicode.org/glossary/#code_point), constant `U+10FFFF`.
     *
     * @since 1.5
     */
    const val MAX_CODE_POINT = 0X10FFFF

    /**
     * The minimum value of a
     * [Unicode supplementary code point](http://www.unicode.org/glossary/#supplementary_code_point), constant `U+10000`.
     *
     * @since 1.5
     */
    const val MIN_SUPPLEMENTARY_CODE_POINT = 0x010000

    /**
     * The minimum value of a
     * [Unicode high-surrogate code unit](http://www.unicode.org/glossary/#high_surrogate_code_unit)
     * in the UTF-16 encoding, constant `'\u005CuD800'`.
     * A high-surrogate is also known as a *leading-surrogate*.
     *
     * @since 1.5
     */
    const val MIN_HIGH_SURROGATE = '\uD800'

    /**
     * The maximum value of a
     * [Unicode high-surrogate code unit](http://www.unicode.org/glossary/#high_surrogate_code_unit)
     * in the UTF-16 encoding, constant `'\u005CuDBFF'`.
     * A high-surrogate is also known as a *leading-surrogate*.
     *
     * @since 1.5
     */
    const val MAX_HIGH_SURROGATE = '\uDBFF'

    /**
     * The minimum value of a
     * [Unicode low-surrogate code unit](http://www.unicode.org/glossary/#low_surrogate_code_unit)
     * in the UTF-16 encoding, constant `'\u005CuDC00'`.
     * A low-surrogate is also known as a *trailing-surrogate*.
     *
     * @since 1.5
     */
    const val MIN_LOW_SURROGATE = '\uDC00'

    /**
     * The maximum value of a
     * [Unicode low-surrogate code unit](http://www.unicode.org/glossary/#low_surrogate_code_unit)
     * in the UTF-16 encoding, constant `'\u005CuDFFF'`.
     * A low-surrogate is also known as a *trailing-surrogate*.
     *
     * @since 1.5
     */
    const val MAX_LOW_SURROGATE = '\uDFFF'

    /**
     * The minimum value of a Unicode surrogate code unit in the
     * UTF-16 encoding, constant `'\u005CuD800'`.
     *
     * @since 1.5
     */
    const val MIN_SURROGATE = MIN_HIGH_SURROGATE

    /**
     * The maximum value of a Unicode surrogate code unit in the
     * UTF-16 encoding, constant `'\u005CuDFFF'`.
     *
     * @since 1.5
     */
    const val MAX_SURROGATE = MAX_LOW_SURROGATE

    /**
     * Determines the number of `char` values needed to
     * represent the specified character (Unicode code point). If the
     * specified character is equal to or greater than 0x10000, then
     * the method returns 2. Otherwise, the method returns 1.
     *
     *
     * This method doesn't validate the specified character to be a
     * valid Unicode code point. The caller must validate the
     * character value using [isValidCodePoint][.isValidCodePoint]
     * if necessary.
     *
     * @param   codePoint the character (Unicode code point) to be tested.
     * @return  2 if the character is a valid supplementary character; 1 otherwise.
     * @see Character.isSupplementaryCodePoint
     * @since   1.5
     */
    fun charCount(codePoint: Int): Int {
        return if (codePoint >= MIN_SUPPLEMENTARY_CODE_POINT) 2 else 1
    }

    /**
     * Returns the code point at the given index of the
     * `CharSequence`. If the `char` value at
     * the given index in the `CharSequence` is in the
     * high-surrogate range, the following index is less than the
     * length of the `CharSequence`, and the
     * `char` value at the following index is in the
     * low-surrogate range, then the supplementary code point
     * corresponding to this surrogate pair is returned. Otherwise,
     * the `char` value at the given index is returned.
     *
     * @param seq a sequence of `char` values (Unicode code
     * units)
     * @param index the index to the `char` values (Unicode
     * code units) in `seq` to be converted
     * @return the Unicode code point at the given index
     * @exception NullPointerException if `seq` is null.
     * @exception IndexOutOfBoundsException if the value
     * `index` is negative or not less than
     * [seq.length()][CharSequence.length].
     * @since  1.5
     */
    fun codePointAt(seq: CharSequence, index: Int): Int {
        var index = index
        val c1 = seq[index]
        if (isHighSurrogate(c1) && ++index < seq.length) {
            val c2 = seq[index]
            if (isLowSurrogate(c2)) {
                return toCodePoint(c1, c2)
            }
        }
        return c1.toInt()
    }

    /**
     * Converts the specified surrogate pair to its supplementary code
     * point value. This method does not validate the specified
     * surrogate pair. The caller must validate it using [ ][.isSurrogatePair] if necessary.
     *
     * @param  high the high-surrogate code unit
     * @param  low the low-surrogate code unit
     * @return the supplementary code point composed from the
     * specified surrogate pair.
     * @since  1.5
     */
    fun toCodePoint(high: Char, low: Char): Int {
        // Optimized form of:
        // return ((high - MIN_HIGH_SURROGATE) << 10)
        //         + (low - MIN_LOW_SURROGATE)
        //         + MIN_SUPPLEMENTARY_CODE_POINT;
        return (high.toInt() shl 10) + low.toInt() + (MIN_SUPPLEMENTARY_CODE_POINT
                - (MIN_HIGH_SURROGATE.toInt() shl 10)
                - MIN_LOW_SURROGATE.toInt())
    }

    /**
     * Determines if the given `char` value is a
     * [Unicode high-surrogate code unit](http://www.unicode.org/glossary/#high_surrogate_code_unit)
     * (also known as *leading-surrogate code unit*).
     *
     *
     * Such values do not represent characters by themselves,
     * but are used in the representation of
     * [supplementary characters](#supplementary)
     * in the UTF-16 encoding.
     *
     * @param  ch the `char` value to be tested.
     * @return `true` if the `char` value is between
     * [.MIN_HIGH_SURROGATE] and
     * [.MAX_HIGH_SURROGATE] inclusive;
     * `false` otherwise.
     * @see Character.isLowSurrogate
     * @see Character.UnicodeBlock.of
     * @since  1.5
     */
    fun isHighSurrogate(ch: Char): Boolean {
        // Help VM constant-fold; MAX_HIGH_SURROGATE + 1 == MIN_LOW_SURROGATE
        return ch >= MIN_HIGH_SURROGATE && ch.toInt() < MAX_HIGH_SURROGATE.toInt() + 1
    }

    /**
     * Determines if the given `char` value is a
     * [Unicode low-surrogate code unit](http://www.unicode.org/glossary/#low_surrogate_code_unit)
     * (also known as *trailing-surrogate code unit*).
     *
     *
     * Such values do not represent characters by themselves,
     * but are used in the representation of
     * [supplementary characters](#supplementary)
     * in the UTF-16 encoding.
     *
     * @param  ch the `char` value to be tested.
     * @return `true` if the `char` value is between
     * [.MIN_LOW_SURROGATE] and
     * [.MAX_LOW_SURROGATE] inclusive;
     * `false` otherwise.
     * @see Character.isHighSurrogate
     * @since  1.5
     */
    fun isLowSurrogate(ch: Char): Boolean {
        return ch >= MIN_LOW_SURROGATE && ch.toInt() < MAX_LOW_SURROGATE.toInt() + 1
    }

    /**
     * Converts the specified character (Unicode code point) to its
     * UTF-16 representation stored in a `char` array. If
     * the specified code point is a BMP (Basic Multilingual Plane or
     * Plane 0) value, the resulting `char` array has
     * the same value as `codePoint`. If the specified code
     * point is a supplementary code point, the resulting
     * `char` array has the corresponding surrogate pair.
     *
     * @param  codePoint a Unicode code point
     * @return a `char` array having
     * `codePoint`'s UTF-16 representation.
     * @exception IllegalArgumentException if the specified
     * `codePoint` is not a valid Unicode code point.
     * @since  1.5
     */
    fun toChars(codePoint: Int): CharArray? {
        return if (isBmpCodePoint(codePoint)) {
            charArrayOf(codePoint.toChar())
        } else if (isValidCodePoint(codePoint)) {
            val result = CharArray(2)
            toSurrogates(codePoint, result, 0)
            result
        } else {
            throw IllegalArgumentException()
        }
    }

    /**
     * Converts the specified character (Unicode code point) to its
     * UTF-16 representation. If the specified code point is a BMP
     * (Basic Multilingual Plane or Plane 0) value, the same value is
     * stored in `dst[dstIndex]`, and 1 is returned. If the
     * specified code point is a supplementary character, its
     * surrogate values are stored in `dst[dstIndex]`
     * (high-surrogate) and `dst[dstIndex+1]`
     * (low-surrogate), and 2 is returned.
     *
     * @param  codePoint the character (Unicode code point) to be converted.
     * @param  dst an array of `char` in which the
     * `codePoint`'s UTF-16 value is stored.
     * @param dstIndex the start index into the `dst`
     * array where the converted value is stored.
     * @return 1 if the code point is a BMP code point, 2 if the
     * code point is a supplementary code point.
     * @exception IllegalArgumentException if the specified
     * `codePoint` is not a valid Unicode code point.
     * @exception NullPointerException if the specified `dst` is null.
     * @exception IndexOutOfBoundsException if `dstIndex`
     * is negative or not less than `dst.length`, or if
     * `dst` at `dstIndex` doesn't have enough
     * array element(s) to store the resulting `char`
     * value(s). (If `dstIndex` is equal to
     * `dst.length-1` and the specified
     * `codePoint` is a supplementary character, the
     * high-surrogate value is not stored in
     * `dst[dstIndex]`.)
     * @since  1.5
     */
    fun toChars(codePoint: Int, dst: CharArray, dstIndex: Int): Int {
        return if (isBmpCodePoint(codePoint)) {
            dst[dstIndex] = codePoint.toChar()
            1
        } else if (isValidCodePoint(codePoint)) {
            toSurrogates(codePoint, dst, dstIndex)
            2
        } else {
            throw IllegalArgumentException()
        }
    }

    fun toSurrogates(codePoint: Int, dst: CharArray, index: Int) {
        // We write elements "backwards" to guarantee all-or-nothing
        dst[index + 1] = lowSurrogate(codePoint)
        dst[index] = highSurrogate(codePoint)
    }

    /**
     * Determines whether the specified code point is a valid
     * [
 * Unicode code point value](http://www.unicode.org/glossary/#code_point).
     *
     * @param  codePoint the Unicode code point to be tested
     * @return `true` if the specified code point value is between
     * [.MIN_CODE_POINT] and
     * [.MAX_CODE_POINT] inclusive;
     * `false` otherwise.
     * @since  1.5
     */
    fun isValidCodePoint(codePoint: Int): Boolean {
        // Optimized form of:
        //     codePoint >= MIN_CODE_POINT && codePoint <= MAX_CODE_POINT
        val plane = codePoint ushr 16
        return plane < MAX_CODE_POINT + 1 ushr 16
    }

    /**
     * Determines whether the specified character (Unicode code point)
     * is in the [Basic Multilingual Plane (BMP)](#BMP).
     * Such code points can be represented using a single `char`.
     *
     * @param  codePoint the character (Unicode code point) to be tested
     * @return `true` if the specified code point is between
     * [.MIN_VALUE] and [.MAX_VALUE] inclusive;
     * `false` otherwise.
     * @since  1.7
     */
    fun isBmpCodePoint(codePoint: Int): Boolean {
        return codePoint ushr 16 == 0
        // Optimized form of:
        //     codePoint >= MIN_VALUE && codePoint <= MAX_VALUE
        // We consistently use logical shift (>>>) to facilitate
        // additional runtime optimizations.
    }

    /**
     * Returns the leading surrogate (a
     * [
 * high surrogate code unit](http://www.unicode.org/glossary/#high_surrogate_code_unit)) of the
     * [
 * surrogate pair](http://www.unicode.org/glossary/#surrogate_pair)
     * representing the specified supplementary character (Unicode
     * code point) in the UTF-16 encoding.  If the specified character
     * is not a
     * [supplementary character](Character.html#supplementary),
     * an unspecified `char` is returned.
     *
     *
     * If
     * [isSupplementaryCodePoint(x)][.isSupplementaryCodePoint]
     * is `true`, then
     * [isHighSurrogate][.isHighSurrogate]`(highSurrogate(x))` and
     * [toCodePoint][.toCodePoint]`(highSurrogate(x), `[lowSurrogate][.lowSurrogate]`(x)) == x`
     * are also always `true`.
     *
     * @param   codePoint a supplementary character (Unicode code point)
     * @return  the leading surrogate code unit used to represent the
     * character in the UTF-16 encoding
     * @since   1.7
     */
    fun highSurrogate(codePoint: Int): Char {
        return ((codePoint ushr 10)
                + (MIN_HIGH_SURROGATE.toInt() - (MIN_SUPPLEMENTARY_CODE_POINT ushr 10))).toChar()
    }

    /**
     * Returns the trailing surrogate (a
     * [
 * low surrogate code unit](http://www.unicode.org/glossary/#low_surrogate_code_unit)) of the
     * [
 * surrogate pair](http://www.unicode.org/glossary/#surrogate_pair)
     * representing the specified supplementary character (Unicode
     * code point) in the UTF-16 encoding.  If the specified character
     * is not a
     * [supplementary character](Character.html#supplementary),
     * an unspecified `char` is returned.
     *
     *
     * If
     * [isSupplementaryCodePoint(x)][.isSupplementaryCodePoint]
     * is `true`, then
     * [isLowSurrogate][.isLowSurrogate]`(lowSurrogate(x))` and
     * [toCodePoint][.toCodePoint]`(`[highSurrogate][.highSurrogate]`(x), lowSurrogate(x)) == x`
     * are also always `true`.
     *
     * @param   codePoint a supplementary character (Unicode code point)
     * @return  the trailing surrogate code unit used to represent the
     * character in the UTF-16 encoding
     * @since   1.7
     */
    fun lowSurrogate(codePoint: Int): Char {
        return ((codePoint and 0x3ff) + MIN_LOW_SURROGATE.toInt()).toChar()
    }
}