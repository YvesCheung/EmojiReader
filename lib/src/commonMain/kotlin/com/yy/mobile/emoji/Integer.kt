package com.yy.mobile.emoji

import kotlin.math.max

/**
 * @author YvesCheung
 * 2020/11/6
 */
internal object Integer {

    /**
     * Returns a string representation of the integer argument as an
     * unsigned integer in base&nbsp;16.
     *
     *
     * The unsigned integer value is the argument plus 2<sup>32</sup>
     * if the argument is negative; otherwise, it is equal to the
     * argument.  This value is converted to a string of ASCII digits
     * in hexadecimal (base&nbsp;16) with no extra leading
     * `0`s.
     *
     *
     * The value of the argument can be recovered from the returned
     * string `s` by calling [ ][Integer.parseUnsignedInt].
     *
     *
     * If the unsigned magnitude is zero, it is represented by a
     * single zero character `'0'` (`'\u005Cu0030'`);
     * otherwise, the first character of the representation of the
     * unsigned magnitude will not be the zero character. The
     * following characters are used as hexadecimal digits:
     *
     * <blockquote>
     * `0123456789abcdef`
    </blockquote> *
     *
     * These are the characters `'\u005Cu0030'` through
     * `'\u005Cu0039'` and `'\u005Cu0061'` through
     * `'\u005Cu0066'`. If uppercase letters are
     * desired, the [java.lang.String.toUpperCase] method may
     * be called on the result:
     *
     * <blockquote>
     * `Integer.toHexString(n).toUpperCase()`
    </blockquote> *
     *
     * @param   i   an integer to be converted to a string.
     * @return  the string representation of the unsigned integer value
     * represented by the argument in hexadecimal (base&nbsp;16).
     * @see .parseUnsignedInt
     * @see .toUnsignedString
     * @since   JDK1.0.2
     */
    fun toHexString(i: Int): String {
        return toUnsignedString0(i, 4)
    }

    /**
     * Convert the integer to an unsigned number.
     */
    private fun toUnsignedString0(`val`: Int, shift: Int): String {
        // assert shift > 0 && shift <=5 : "Illegal shift value";
        val mag: Int = Int.SIZE_BITS - numberOfLeadingZeros(`val`)
        val chars: Int = max((mag + (shift - 1)) / shift, 1)
        val buf = CharArray(chars)
        formatUnsignedInt(`val`, shift, buf, 0, chars)

        // Android-changed: Use regular constructor instead of one which takes over "buf".
        // return new String(buf, true);
        return String(buf)
    }

    /**
     * Returns the number of zero bits preceding the highest-order
     * ("leftmost") one-bit in the two's complement binary representation
     * of the specified `int` value.  Returns 32 if the
     * specified value has no one-bits in its two's complement representation,
     * in other words if it is equal to zero.
     *
     *
     * Note that this method is closely related to the logarithm base 2.
     * For all positive `int` values x:
     *
     *  * floor(log<sub>2</sub>(x)) = `31 - numberOfLeadingZeros(x)`
     *  * ceil(log<sub>2</sub>(x)) = `32 - numberOfLeadingZeros(x - 1)`
     *
     *
     * @param i the value whose number of leading zeros is to be computed
     * @return the number of zero bits preceding the highest-order
     * ("leftmost") one-bit in the two's complement binary representation
     * of the specified `int` value, or 32 if the value
     * is equal to zero.
     * @since 1.5
     */
    fun numberOfLeadingZeros(i: Int): Int {
        // HD, Figure 5-6
        var i = i
        if (i == 0) return 32
        var n = 1
        if (i ushr 16 == 0) {
            n += 16
            i = i shl 16
        }
        if (i ushr 24 == 0) {
            n += 8
            i = i shl 8
        }
        if (i ushr 28 == 0) {
            n += 4
            i = i shl 4
        }
        if (i ushr 30 == 0) {
            n += 2
            i = i shl 2
        }
        n -= i ushr 31
        return n
    }

    /**
     * Format a long (treated as unsigned) into a character buffer.
     * @param val the unsigned int to format
     * @param shift the log2 of the base to format in (4 for hex, 3 for octal, 1 for binary)
     * @param buf the character buffer to write to
     * @param offset the offset in the destination buffer to start at
     * @param len the number of characters to write
     * @return the lowest character  location used
     */
    fun formatUnsignedInt(`val`: Int, shift: Int, buf: CharArray, offset: Int, len: Int): Int {
        var `val` = `val`
        var charPos = len
        val radix = 1 shl shift
        val mask = radix - 1
        do {
            buf[offset + --charPos] = digits[`val` and mask]
            `val` = `val` ushr shift
        } while (`val` != 0 && charPos > 0)
        return charPos
    }

    /**
     * All possible chars for representing a number as a String
     */
    val digits = charArrayOf(
        '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b',
        'c', 'd', 'e', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z'
    )
}