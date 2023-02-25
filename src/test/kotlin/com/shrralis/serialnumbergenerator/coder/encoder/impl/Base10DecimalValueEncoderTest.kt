/*
 * The following code is created by shrralis.
 *
 *  - Base10DecimalValueEncoderTest.kt (Last change: 2/25/23, 4:36 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.coder.encoder.impl

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
class Base10DecimalValueEncoderTest {

    private val instance = Base10DecimalValueEncoder()

    @ParameterizedTest
    @MethodSource("getData")
    fun `encoding works properly`(rawValue: Long, expectedEncoded: String) {
        assertThat(instance.encode(rawValue)).isEqualTo(expectedEncoded)
    }

    @ParameterizedTest
    @MethodSource("getData")
    fun `zero field has no influence on encoding process`(rawValue: Long, expectedEncoded: String) {
        val instanceWithCustomZero = Base10DecimalValueEncoder('1')

        assertThat(instanceWithCustomZero.encode(rawValue)).isEqualTo(expectedEncoded)
    }

    @Test
    fun `isSuitable() returns true on base-10 radix`() {
        assertThat(instance.isSuitable(10)).isTrue
    }

    @Test
    fun `isSuitable() returns false on non-base-10 radix`() {
        assertThat(instance.isSuitable(11)).isFalse
        assertThat(instance.isSuitable(0)).isFalse
        assertThat(instance.isSuitable(-10)).isFalse
    }

    companion object {

        @JvmStatic
        private fun getData(): List<Arguments> = (0L..1234).map { arguments(it, "$it") }
    }
}
