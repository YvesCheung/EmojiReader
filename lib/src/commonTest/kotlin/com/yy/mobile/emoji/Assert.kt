package com.yy.mobile.emoji

/**
 * @author YvesCheung
 * 2020/11/6
 */
object Assert {

    fun assertFalse(message: String, actual: Boolean) {
        kotlin.test.assertFalse(actual, message)
    }

    fun assertTrue(message: String, actual: Boolean) {
        kotlin.test.assertTrue(actual, message)
    }

    fun assertEquals(a: Any?, b: Any?) {
        kotlin.test.assertEquals(a, b)
    }
}