/*
 * The following code is created by shrralis.
 *
 *  - AbstractGeneratedSerialNumberPartConfigConverter.kt (Last change: 2/24/23, 11:48 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.converter.format.part

import com.shrralis.serialnumbergenerator.config.DEFAULT_GENERATED_PART_PATTERN
import com.shrralis.serialnumbergenerator.config.DEFAULT_GENERATED_PART_WIDTH_PATTERN

/**
 * An abstract base class for converters of generated serial number part configurations.
 *
 * @param rawFormatPartRegex the regular expression pattern that the raw format part must match.
 * @param rawFormatPartWidthRegex the regular expression pattern that the width of the raw format part
 * can be extracted from.
 *
 * @throws IllegalArgumentException when `rawFormatPartRegex` is blank or [rawFormatPartWidthRegex] is blank.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
abstract class AbstractGeneratedSerialNumberPartConfigConverter(
    rawFormatPartRegex: Regex = Regex(DEFAULT_GENERATED_PART_PATTERN),
    private val rawFormatPartWidthRegex: Regex = Regex(DEFAULT_GENERATED_PART_WIDTH_PATTERN),
) : SerialNumberPartConfigConverter {

    private val partRegex: Regex = Regex("^${rawFormatPartRegex.pattern}\$")

    init {
        require(rawFormatPartRegex.pattern.isNotBlank()) { "[rawFormatPartRegex] cannot be blank" }
        require(rawFormatPartWidthRegex.pattern.isNotBlank()) { "[rawFormatPartWidthRegex] cannot be blank" }
    }

    override fun isSuitable(rawFormatPart: String): Boolean = partRegex.matches(rawFormatPart)

    /**
     * Extracts the width of the generated serial number part configuration from the raw format part.
     *
     * @param rawFormatPart the raw format part to extract the width from.
     * @return the width of the generated serial number part configuration.
     *
     * @throws IllegalArgumentException if the width can't be extracted from the raw format part.
     */
    protected fun extractPartWidth(rawFormatPart: String): Int = rawFormatPartWidthRegex.find(rawFormatPart)
        ?.groupValues
        ?.first()
        ?.toInt()
        ?: throw IllegalArgumentException(
            "The width couldn't be extracted from [rawFormatPart = '$rawFormatPart'] " +
                "with '$rawFormatPartWidthRegex' regex"
        )
}
