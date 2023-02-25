/*
 * The following code is created by shrralis.
 *
 *  - AbstractGeneratedSerialNumberPartConfigConverterTest.kt (Last change: 2/25/23, 12:11 AM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.converter.format.part

import com.shrralis.serialnumbergenerator.config.format.part.SerialNumberPartConfig
import org.assertj.core.api.Assertions.assertThat
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
class AbstractGeneratedSerialNumberPartConfigConverterTest {

    private val instance = TestConverter(
        DEFAULT_RAW_FORMAT_PART_REGEX.toRegex(),
        DEFAULT_RAW_FORMAT_PART_WIDTH_REGEX.toRegex(),
    )

    @ParameterizedTest
    @MethodSource("getData")
    fun `isSuitable() returns true for inputs that fully match rawFormatPartRegex`(rawFormatPart: String) {
        assertThat(instance.isSuitable(rawFormatPart)).isTrue
    }

    @ParameterizedTest
    @MethodSource("getData")
    fun `isSuitable() returns false for inputs that do not fully match rawFormatPartRegex`(rawFormatPart: String) {
        assertThat(instance.isSuitable(" $rawFormatPart")).isFalse
    }

    @ParameterizedTest
    @MethodSource("getData")
    fun `extractPartWidth() returns proper extracted width`(rawFormatPart: String, expectedWidth: Int) {
        assertThat(instance.exposedExtractPartWidth(rawFormatPart)).isEqualTo(expectedWidth)
    }

    @Test
    fun `extractPartWidth() throws IllegalArgumentException if the width cannot be extracted`() {
        val badRawFormatPart = "Z_13"
        assertThrows<IllegalArgumentException> { instance.exposedExtractPartWidth(badRawFormatPart) }.also {
            assertThat(it.message).isEqualTo(
                "The width couldn't be extracted from [rawFormatPart = '$badRawFormatPart'] " +
                    "with '$DEFAULT_RAW_FORMAT_PART_WIDTH_REGEX' regex"
            )
        }
    }

    companion object {

        private const val DEFAULT_RAW_FORMAT_PART_REGEX = "[Z9]_\\d+_"
        private const val DEFAULT_RAW_FORMAT_PART_WIDTH_REGEX = "(?<=_)\\d+(?=_)"

        @JvmStatic
        private fun getData(): List<Arguments> = listOf(
            arguments("Z_1_", 1),
            arguments("Z_154_", 154),
            arguments("9_2_", 2),
            arguments("9_35_", 35),
        )
    }
}

private class TestConverter(rawFormatPartRegex: Regex, rawFormatPartWidthRegex: Regex) :
    AbstractGeneratedSerialNumberPartConfigConverter(rawFormatPartRegex, rawFormatPartWidthRegex) {

    override fun convert(rawFormatPart: String): SerialNumberPartConfig =
        TODO("No need to implement it for these tests")

    fun exposedExtractPartWidth(rawFormatPart: String) = extractPartWidth(rawFormatPart)
}
