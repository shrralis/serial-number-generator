/*
 * The following code is created by shrralis.
 *
 *  - Base26DecimalValueDecoderTest.kt (Last change: 2/25/23, 4:36 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.coder.decoder.impl

import com.shrralis.serialnumbergenerator.convertBase10ToBase26
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

/**
 * @author shrralis
 * @see [https://t.me/Shrralis](https://t.me/Shrralis)
 */
class Base26DecimalValueDecoderTest {

    private val instance = Base26DecimalValueDecoder()

    @ParameterizedTest
    @MethodSource("getDataWhenZeroIsA")
    fun `decoding works properly`(encodedValue: String, expectedDecimal: Long) {
        assertThat(instance.decode(encodedValue)).isEqualTo(expectedDecimal)
    }

    @ParameterizedTest
    @MethodSource("getDataWhenZeroIs0")
    fun `zero field has no influence on decoding process`(encodedValue: String, expectedDecimal: Long) {
        val instanceWithCustomZero = Base26DecimalValueDecoder('0')

        assertThat(instanceWithCustomZero.decode(encodedValue)).isEqualTo(expectedDecimal)
    }

    @Test
    fun `isSuitable() returns true on base-26 radix`() {
        assertThat(instance.isSuitable(26)).isTrue
    }

    @Test
    fun `isSuitable() returns false on non-base-26 radix`() {
        assertThat(instance.isSuitable(27)).isFalse
        assertThat(instance.isSuitable(0)).isFalse
        assertThat(instance.isSuitable(-26)).isFalse
    }

    companion object {

        @JvmStatic
        private fun getDataWhenZeroIsA(): List<Arguments> =
            (0L..1234).map { arguments(convertBase10ToBase26(it, 'A'), it) }

        @JvmStatic
        private fun getDataWhenZeroIs0(): List<Arguments> =
            (0L..1234).map { arguments(convertBase10ToBase26(it, '0'), it) }
    }
}
