/*
 * The following code is created by shrralis.
 *
 *  - Base26SerialNumberPartConfigConverter.kt (Last change: 2/24/23, 9:56 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.converter.format.part.impl.generated

import com.shrralis.serialnumbergenerator.config.DEFAULT_BASE_26_PART_PATTERN
import com.shrralis.serialnumbergenerator.config.DEFAULT_GENERATED_PART_WIDTH_PATTERN
import com.shrralis.serialnumbergenerator.config.format.part.SerialNumberPartConfig
import com.shrralis.serialnumbergenerator.config.format.part.impl.generated.Base26SerialNumberPartConfig
import com.shrralis.serialnumbergenerator.converter.format.part.AbstractGeneratedSerialNumberPartConfigConverter

/**
 * A converter class for a base-26 generated serial number part configuration.
 *
 * @param rawFormatPartRegex the regular expression pattern that the raw format part must match.
 * @param rawFormatPartWidthRegex the regular expression pattern that the width of the raw format part
 * can be extracted from.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
class Base26SerialNumberPartConfigConverter(
    rawFormatPartRegex: Regex = Regex(DEFAULT_BASE_26_PART_PATTERN),
    rawFormatPartWidthRegex: Regex = Regex(DEFAULT_GENERATED_PART_WIDTH_PATTERN),
) : AbstractGeneratedSerialNumberPartConfigConverter(rawFormatPartRegex, rawFormatPartWidthRegex) {

    override fun convert(rawFormatPart: String): SerialNumberPartConfig =
        Base26SerialNumberPartConfig(extractPartWidth(rawFormatPart))
}
