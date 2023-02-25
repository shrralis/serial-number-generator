/*
 * The following code is created by shrralis.
 *
 *  - SerialNumberService.kt (Last change: 2/25/23, 7:40 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator

import com.shrralis.serialnumbergenerator.coder.decoder.DecimalValueDecoder
import com.shrralis.serialnumbergenerator.coder.encoder.DecimalValueEncoder
import com.shrralis.serialnumbergenerator.config.format.SerialNumberFormatConfig
import com.shrralis.serialnumbergenerator.config.format.part.AbstractGeneratedSerialNumberPartConfig
import com.shrralis.serialnumbergenerator.config.format.part.impl.static.StaticSerialNumberPartConfig

/**
 * A [SerialNumberGenerator] and [SerialNumberParser] implementation that generates and parses sequential serial numbers
 * based on a [SerialNumberFormatConfig].
 *
 * The format is flexible and can be configured with placeholders for the generated parts
 * of the serial number, allowing for a wide range of formats to be used.
 * - For example, the format string "A{2}0{3}" would generate serial numbers in the format "AA001", "AA002", "AA003",
 * and so on, incrementing the numeric portion of the code sequentially.
 * - Similarly, the format string "ZA{2}0{3}" would generate serial numbers in the format "ZAA001", "ZAA002", "ZAA003",
 * and so on, but in this case the "Z" part is static since it is not appended with "{n}" width.
 * - Another example of possible format would be: "0{3}A{2}", it would generate serial numbers
 * in format "000AB", "000AC", "000AD", and so on.
 *
 * Note that the format property of the [SerialNumberFormatConfig] object should include only the placeholders and
 * any static (not appended with "{n}" width) values in the format.
 *
 * @param formatConfig the [SerialNumberFormatConfig] object that specifies the format of the serial numbers to be generated.
 *
 * @throws IllegalArgumentException if the format config is invalid, i.e., if the format string is invalid or if any
 * of the part configurations are invalid.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
class SerialNumberService(
    private val formatConfig: SerialNumberFormatConfig,
    private val decimalValueEncoders: List<DecimalValueEncoder>,
    private val decimalValueDecoders: List<DecimalValueDecoder>,
) : SerialNumberGenerator, SerialNumberParser {

    init {
        require(formatConfig.partConfigs.isNotEmpty()) { "Invalid code format. There's no part to generate." }
        require(formatConfig.serialNumberLength > 0) { "Invalid code format. Bad length of serial numbers." }
    }

    /**
     * Generates a sequential serial number based on the given decimal value,
     * using the format specified in the format config.
     * The generated serial number consists of one or more parts, with each part corresponding
     * to a placeholder in the format string.
     * The width and radix of each part are also specified in the format config.
     *
     * @param decimalValue the decimal value to generate a serial number for.
     * @return the generated serial number as a string.
     */
    override fun generate(decimalValue: Long): String {
        require(decimalValue > 0) { "Only positive values are supported" }
        require(decimalValue <= formatConfig.maxDecimalValue) {
            "$decimalValue value is greater than maximum possible ${formatConfig.maxDecimalValue}"
        }

        return formatConfig.partConfigs.reversed().fold(decimalValue to "") { (remainder, serialNumber), partConfig ->
            when (partConfig) {
                is StaticSerialNumberPartConfig -> remainder to serialNumber.prepend(partConfig.value)
                is AbstractGeneratedSerialNumberPartConfig -> {
                    val radix = partConfig.radix
                    val width = partConfig.width

                    val wideRadix = partConfig.maxDecimalValue.inc()
                    val value = remainder % wideRadix

                    require(value <= partConfig.maxDecimalValue) {
                        "$value part value is greater than maximum possible ${partConfig.maxDecimalValue}"
                    }

                    val encoder = decimalValueEncoders.find { it.isSuitable(radix) }
                        ?: throw IllegalArgumentException("No DecimalValueEncoder found for radix = $radix")

                    val serialNumberPartValue = encoder.encode(value)

                    (remainder / wideRadix) to serialNumber.prepend(serialNumberPartValue.padStart(width, encoder.zero))
                }

                else -> throw IllegalArgumentException("Unsupported SerialNumberPartConfig")
            }
        }.second
    }

    /**
     * Parses the given sequential serial number and returns the decimal value it represents,
     * based on the format specified in the format config.
     * The serial number is parsed into one or more parts, with each part corresponding to a placeholder
     * in the format string.
     * The width and radix of each part are also specified in the format config.
     *
     * @param serialNumber the sequential serial number to parse.
     * @return the decimal value represented by the serial number.
     *
     * @throws IllegalArgumentException if the serial number is invalid, i.e., if it does not match
     * the format specified in the format config, or if any of the parts are not valid for their respective radix.
     */
    override fun parse(serialNumber: String): Long {
        require(serialNumber.length == formatConfig.serialNumberLength) {
            "Invalid serial number: expected length ${formatConfig.serialNumberLength}"
        }

        return formatConfig.partConfigs.reversed()
            .fold(serialNumber to 0L to 1L) { (remainder, parsed, multiplier), partConfig ->
                when (partConfig) {
                    is StaticSerialNumberPartConfig -> {
                        require(remainder.endsWith(partConfig.value)) {
                            "Invalid serial number: expected '${partConfig.value}'"
                        }
                        remainder.dropLast(partConfig.width) to parsed to multiplier
                    }

                    is AbstractGeneratedSerialNumberPartConfig -> {
                        val radix = partConfig.radix
                        val width = partConfig.width

                        require(remainder.length >= width) {
                            "Invalid serial number: expected $width digits of $radix radix"
                        }

                        val partValue = remainder.takeLast(width)

                        val decoder = decimalValueDecoders.find { it.isSuitable(radix) }
                            ?: throw IllegalArgumentException("No DecimalValueDecoder found for radix = $radix")

                        val decodedValue = decoder.decode(partValue)

                        remainder.dropLast(width) to
                            parsed + decodedValue * multiplier to
                            multiplier * partConfig.maxDecimalValue.inc()
                    }

                    else -> throw IllegalArgumentException("Unsupported SerialNumberPartConfig")
                }
            }.second
    }

    private fun String.prepend(s: String) = "$s$this"

    private infix fun <F, S, T> Pair<F, S>.to(third: T) = Triple(first, second, third)
}
