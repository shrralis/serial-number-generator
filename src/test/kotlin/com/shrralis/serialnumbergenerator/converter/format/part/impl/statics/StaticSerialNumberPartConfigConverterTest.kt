/*
 * The following code is created by shrralis.
 *
 *  - StaticSerialNumberPartConfigConverterTest.kt (Last change: 2/25/23, 12:11 AM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.converter.format.part.impl.statics

import com.shrralis.serialnumbergenerator.config.format.part.impl.static.StaticSerialNumberPartConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

/**
 * @author shrralis
 * @see [https://t.me/Shrralis](https://t.me/Shrralis)
 */
class StaticSerialNumberPartConfigConverterTest {

    private val instance = StaticSerialNumberPartConfigConverter()

    @ParameterizedTest
    @MethodSource("getData")
    fun `convert() always returns StaticSerialNumberPartConfig with the raw input`(s: String) {
        assertThat(instance.convert(s)).isInstanceOf(StaticSerialNumberPartConfig::class.java)
            .extracting { it as StaticSerialNumberPartConfig }
            .run {
                extracting { it.value }.isEqualTo(s)
                extracting { it.width }.isEqualTo(s.length)
            }
    }

    @Test
    fun `convert() throws IllegalArgumentException on empty input`() {
        assertThrows<IllegalArgumentException> { instance.convert("") }.also {
            assertThat(it.message).isEqualTo("Value should not be empty")
        }
    }

    @ParameterizedTest
    @MethodSource("getData")
    fun `isSuitable() always returns true for non-empty strings`(s: String) {
        assertThat(instance.isSuitable(s)).isTrue
    }

    @Test
    fun `isSuitable() returns false for empty string`() {
        assertThat(instance.isSuitable("")).isFalse
    }

    companion object {

        @JvmStatic
        private fun getData(): List<Arguments> = listOf(
            arguments("0"),
            arguments("1"),
            arguments("10"),
            arguments("100"),
            arguments("1000"),
            arguments("-1000"),
            arguments("A"),
            arguments("ABCD"),
            arguments("ABCD{}!@$%^"),
        )
    }
}
