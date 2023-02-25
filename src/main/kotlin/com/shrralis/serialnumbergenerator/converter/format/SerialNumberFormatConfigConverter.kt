/*
 * The following code is created by shrralis.
 *
 *  - SerialNumberFormatConfigConverter.kt (Last change: 2/24/23, 9:59 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.converter.format

import com.shrralis.serialnumbergenerator.config.format.SerialNumberFormatConfig

/**
 * An interface for converting a raw serial number format string into a [SerialNumberFormatConfig] instance.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
interface SerialNumberFormatConfigConverter {

    /**
     * Converts a raw serial number format string into a [SerialNumberFormatConfig] instance.
     *
     * @param rawFormat the raw serial number format string to convert.
     * @return a [SerialNumberFormatConfig] instance that represents the raw serial number format string.
     */
    fun convert(rawFormat: String): SerialNumberFormatConfig
}
