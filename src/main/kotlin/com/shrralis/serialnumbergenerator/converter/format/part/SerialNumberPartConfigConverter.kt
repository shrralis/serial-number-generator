/*
 * The following code is created by shrralis.
 *
 *  - SerialNumberPartConfigConverter.kt (Last change: 2/24/23, 9:56 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.converter.format.part

import com.shrralis.serialnumbergenerator.config.format.part.SerialNumberPartConfig

/**
 * An interface for converting raw serial number format parts into corresponding [SerialNumberPartConfig] objects.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
interface SerialNumberPartConfigConverter {

    /**
     * Converts a raw serial number format part into the corresponding serial number part configuration object.
     *
     * @param rawFormatPart the raw serial number format part to convert.
     * @return the corresponding [SerialNumberPartConfig] object.
     */
    fun convert(rawFormatPart: String): SerialNumberPartConfig

    /**
     * Checks if a raw serial number format part is suitable for conversion by this converter.
     *
     * @param rawFormatPart the raw serial number format part to check.
     * @return `true` if the raw serial number format part is suitable for conversion, `false` otherwise.
     */
    fun isSuitable(rawFormatPart: String): Boolean
}
