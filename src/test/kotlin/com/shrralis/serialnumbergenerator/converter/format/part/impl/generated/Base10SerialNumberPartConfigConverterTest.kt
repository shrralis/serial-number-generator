/*
 * The following code is created by shrralis.
 *
 *  - Base10SerialNumberPartConfigConverterTest.kt (Last change: 2/25/23, 12:21 AM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.converter.format.part.impl.generated

import com.shrralis.serialnumbergenerator.config.format.part.impl.generated.Base10SerialNumberPartConfig
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
class Base10SerialNumberPartConfigConverterTest {

    private val instance = Base10SerialNumberPartConfigConverter()

    @ParameterizedTest
    @MethodSource("getData")
    fun `convert() always returns Base10SerialNumberPartConfig with the proper width`(
        rawFormatPart: String,
        expectedWidth: Int,
    ) {
        assertThat(instance.convert(rawFormatPart)).isInstanceOf(Base10SerialNumberPartConfig::class.java)
            .extracting { it as Base10SerialNumberPartConfig }
            .extracting { it.width }
            .isEqualTo(expectedWidth)
    }

    @Test
    fun `convert() throws IllegalArgumentException on 0 width`() {
        assertThrows<IllegalArgumentException> { instance.convert("0{0}") }.also {
            assertThat(it.message).isEqualTo("Width must be positive value")
        }
    }

    companion object {

        @JvmStatic
        private fun getData(): List<Arguments> = listOf(
            arguments("0{1}", 1),
            arguments("0{201}", 201),
            // the following cases are not valid since default regexes suppose 'A' represents base-26
            // but the width still may be extracted properly,
            // and we suppose that invocation of `isSuitable()` before invoking `convert()` should be enough
            arguments("A{1}", 1),
            arguments("A{1000}", 1000),
        )
    }
}
