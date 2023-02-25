/*
 * The following code is created by shrralis.
 *
 *  - SerialNumberServiceTest.kt (Last change: 2/25/23, 7:42 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator

import com.shrralis.serialnumbergenerator.coder.decoder.impl.Base10DecimalValueDecoder
import com.shrralis.serialnumbergenerator.coder.decoder.impl.Base26DecimalValueDecoder
import com.shrralis.serialnumbergenerator.coder.encoder.impl.Base10DecimalValueEncoder
import com.shrralis.serialnumbergenerator.coder.encoder.impl.Base26DecimalValueEncoder
import com.shrralis.serialnumbergenerator.config.format.SerialNumberFormatConfig
import com.shrralis.serialnumbergenerator.config.format.part.SerialNumberPartConfig
import com.shrralis.serialnumbergenerator.config.format.part.impl.generated.Base10SerialNumberPartConfig
import com.shrralis.serialnumbergenerator.config.format.part.impl.generated.Base26SerialNumberPartConfig
import com.shrralis.serialnumbergenerator.config.format.part.impl.static.StaticSerialNumberPartConfig
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
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
class SerialNumberServiceTestIT {

    private val formatConfig = SerialNumberFormatConfig(
        listOf(
            Base26SerialNumberPartConfig(2),
            StaticSerialNumberPartConfig("_"),
            Base10SerialNumberPartConfig(3),
        )
    )

    @SpyK
    private var base10DecimalValueEncoder = Base10DecimalValueEncoder()

    @SpyK
    private var base10DecimalValueDecoder = Base10DecimalValueDecoder()

    @SpyK
    private var base26DecimalValueEncoder = Base26DecimalValueEncoder()

    @SpyK
    private var base26DecimalValueDecoder = Base26DecimalValueDecoder()

    private lateinit var instance: SerialNumberService

    @BeforeEach
    fun setup() {
        instance = SerialNumberService(
            formatConfig,
            listOf(base10DecimalValueEncoder, base26DecimalValueEncoder),
            listOf(base10DecimalValueDecoder, base26DecimalValueDecoder),
        )
    }

    @ParameterizedTest
    @MethodSource("getData")
    fun `generates proper values`(decimalValue: Long, expectedSerialNumber: String) {
        assertThat(instance.generate(decimalValue)).isEqualTo(expectedSerialNumber)
    }

    @ParameterizedTest
    @MethodSource("getData")
    fun `parses proper values`(expectedDecimalValue: Long, serialNumber: String) {
        assertThat(instance.parse(serialNumber)).isEqualTo(expectedDecimalValue)
    }

    @Test
    fun `creating instance throws IllegalArgumentException when formatConfig has no partConfigs`() {
        val badFormatConfig = SerialNumberFormatConfig(emptyList())
        assertThrows<IllegalArgumentException> { SerialNumberService(badFormatConfig, listOf(), listOf()) }.also {
            assertThat(it.message).isEqualTo("Invalid code format. There's no part to generate.")
        }
    }

    @Test
    fun `creating instance throws IllegalArgumentException when serialNumberLength of formatConfig is smaller or equal 0`() {
        val badFormatConfig = mockk<SerialNumberFormatConfig>().apply {
            every { partConfigs } returns listOf(mockk())
            every { serialNumberLength } returns 0
        }

        assertThrows<IllegalArgumentException> { SerialNumberService(badFormatConfig, listOf(), listOf()) }.also {
            assertThat(it.message).isEqualTo("Invalid code format. Bad length of serial numbers.")
        }

        every { badFormatConfig.serialNumberLength } returns -1

        assertThrows<IllegalArgumentException> { SerialNumberService(badFormatConfig, listOf(), listOf()) }.also {
            assertThat(it.message).isEqualTo("Invalid code format. Bad length of serial numbers.")
        }
    }

    @Test
    fun `generate() throws IllegalArgumentException when input is negative value`() {
        assertThrows<IllegalArgumentException> { instance.generate(-1) }.also {
            assertThat(it.message).isEqualTo("Only positive values are supported")
        }
    }

    @Test
    fun `generate() throws IllegalArgumentException when input is greater than maximum possible value of format`() {
        val max = formatConfig.maxDecimalValue
        val input = max.inc()

        assertThrows<IllegalArgumentException> { instance.generate(input) }.also {
            assertThat(it.message).isEqualTo("$input value is greater than maximum possible $max")
        }
    }

    @Test
    fun `generate() throws IllegalArgumentException when no suitable encoder available`() {
        val customInstance = SerialNumberService(formatConfig, listOf(), listOf())
        val input = 1L

        assertThrows<IllegalArgumentException> { customInstance.generate(input) }.also {
            assertThat(it.message).startsWith("No DecimalValueEncoder found for radix =")
        }
    }

    @Test
    fun `generate() throws IllegalArgumentException when formatConfig contains unsupported partConfig`() {
        val customFormatConfig = SerialNumberFormatConfig(
            listOf(
                object : SerialNumberPartConfig {
                    override val width: Int = 1
                },
                Base10SerialNumberPartConfig(1), // this is needed, so we'd have a maximum possible value
            )
        )
        val customInstance = SerialNumberService(customFormatConfig, listOf(base10DecimalValueEncoder), listOf())

        assertThrows<IllegalArgumentException> { customInstance.generate(1) }.also {
            assertThat(it.message).isEqualTo("Unsupported SerialNumberPartConfig")
        }
    }

    @Test
    fun `parse() throws IllegalArgumentException when input length is not equal to format length`() {
        val validLength = formatConfig.serialNumberLength
        val expectedMessage = "Invalid serial number: expected length ${formatConfig.serialNumberLength}"

        val tooLong = (0..validLength).joinToString("") { "A" }
        assertThrows<IllegalArgumentException> { instance.parse(tooLong) }.also {
            assertThat(it.message).isEqualTo(expectedMessage)
        }

        val tooShort = (1 until validLength).joinToString("") { "A" }
        assertThrows<IllegalArgumentException> { instance.parse(tooShort) }.also {
            assertThat(it.message).isEqualTo(expectedMessage)
        }
    }

    @Test
    fun `parse() throws IllegalArgumentException when static part is not in the proper place`() {
        assertThrows<IllegalArgumentException> { instance.parse("_AA001") }.also {
            assertThat(it.message).isEqualTo("Invalid serial number: expected '_'")
        }
    }

    @Test
    fun `parse() throws IllegalArgumentException when no suitable decoder available`() {
        val customInstance = SerialNumberService(formatConfig, listOf(), listOf())
        val input = "AA_001"

        assertThrows<IllegalArgumentException> { customInstance.parse(input) }.also {
            assertThat(it.message).startsWith("No DecimalValueDecoder found for radix =")
        }
    }

    @Test
    fun `parse() throws IllegalArgumentException when formatConfig contains unsupported partConfig`() {
        val customFormatConfig = SerialNumberFormatConfig(
            listOf(
                object : SerialNumberPartConfig {
                    override val width: Int = 14
                },
            )
        )
        val customInstance = SerialNumberService(customFormatConfig, listOf(base10DecimalValueEncoder), listOf())

        assertThrows<IllegalArgumentException> { customInstance.parse("doesn't matter") }.also {
            assertThat(it.message).isEqualTo("Unsupported SerialNumberPartConfig")
        }
    }

    companion object {

        @JvmStatic
        private fun getData(): List<Arguments> = listOf(
            arguments(1, "AA_001"),
            arguments(2, "AA_002"),
            arguments(999, "AA_999"),
            arguments(1000, "AB_000"),
            arguments(1001, "AB_001"),
            arguments(1002, "AB_002"),
            arguments(12345, "AM_345"),
            arguments(675999, "ZZ_999"),
        )
    }
}
