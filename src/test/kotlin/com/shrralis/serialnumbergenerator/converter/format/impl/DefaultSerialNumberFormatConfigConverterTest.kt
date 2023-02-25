/*
 * The following code is created by shrralis.
 *
 *  - DefaultSerialNumberFormatConfigConverterTest.kt (Last change: 2/25/23, 11:36 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.converter.format.impl

import com.shrralis.serialnumbergenerator.converter.format.part.SerialNumberPartConfigConverter
import com.shrralis.serialnumbergenerator.splitter.SerialNumberFormatPartSplitter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

private const val PART_1 = "Part 1"
private const val PART_2 = "Part 2"
private const val PART_3 = "Part 3"

/**
 * @author shrralis
 * @see [https://t.me/Shrralis](https://t.me/Shrralis)
 */
class DefaultSerialNumberFormatConfigConverterTest {

    @RelaxedMockK
    private lateinit var part1ConfigConverter: SerialNumberPartConfigConverter

    @RelaxedMockK
    private lateinit var part2ConfigConverter: SerialNumberPartConfigConverter

    @RelaxedMockK
    private lateinit var part3ConfigConverter: SerialNumberPartConfigConverter

    @MockK
    private lateinit var serialNumberFormatPartSplitter: SerialNumberFormatPartSplitter

    private lateinit var partConfigConverters: List<SerialNumberPartConfigConverter>

    private lateinit var instance: DefaultSerialNumberFormatConfigConverter

    @BeforeEach
    fun setup() {
        partConfigConverters = listOf(
            part1ConfigConverter,
            part2ConfigConverter,
            part3ConfigConverter,
        )
        instance = DefaultSerialNumberFormatConfigConverter(serialNumberFormatPartSplitter, partConfigConverters)

        every { serialNumberFormatPartSplitter.split(any()) } returns listOf(PART_1, PART_2, PART_3)

        mapOf(
            PART_1 to part1ConfigConverter,
            PART_2 to part2ConfigConverter,
            PART_3 to part3ConfigConverter,
        ).forEach { (part, converter) ->
            every { converter.isSuitable(any()) } returns false
            every { converter.isSuitable(part) } returns true
        }
    }

    @Test
    fun `converts rawFormat properly`() {
        val rawFormat = "it doesn't matter, we mocked [serialNumberFormatPartSplitter]"
        // since the result doesn't really matter here, we just need to invoke it
        // to see the splitter and proper partConfigConverters invoked nicely
        instance.convert(rawFormat)

        verify(exactly = 1) { serialNumberFormatPartSplitter.split(rawFormat) }
        verify(exactly = 1) { part1ConfigConverter.convert(PART_1) }
        verify(exactly = 1) { part2ConfigConverter.convert(PART_2) }
        verify(exactly = 1) { part3ConfigConverter.convert(PART_3) }
    }

    @Test
    fun `throws IllegalArgumentException when no suitable SerialNumberFormatPartSplitter found`() {
        val badPart = "I am bad guy"

        every { serialNumberFormatPartSplitter.split(badPart) } returns listOf(badPart)

        assertThrows<IllegalArgumentException> { instance.convert(badPart) }.also {
            assertThat(it.message).isEqualTo("No SerialNumberPartConfigConverter found for part '$badPart'")
        }
    }
}
