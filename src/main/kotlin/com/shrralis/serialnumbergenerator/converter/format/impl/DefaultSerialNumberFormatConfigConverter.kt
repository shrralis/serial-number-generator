/*
 * The following code is created by shrralis.
 *
 *  - DefaultSerialNumberFormatConfigConverter.kt (Last change: 2/25/23, 4:22 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.converter.format.impl

import com.shrralis.serialnumbergenerator.config.format.SerialNumberFormatConfig
import com.shrralis.serialnumbergenerator.config.format.part.SerialNumberPartConfig
import com.shrralis.serialnumbergenerator.converter.format.SerialNumberFormatConfigConverter
import com.shrralis.serialnumbergenerator.converter.format.part.SerialNumberPartConfigConverter
import com.shrralis.serialnumbergenerator.splitter.SerialNumberFormatPartSplitter

/**
 * A default implementation of the [SerialNumberFormatConfigConverter] interface.
 *
 * @property serialNumberFormatPartSplitter a [SerialNumberFormatPartSplitter] instance used to split
 * the raw format string into parts.
 * @property partConfigConverters a list of [SerialNumberPartConfigConverter] instances used to convert
 * each part of the raw format string into a [SerialNumberPartConfig] instance.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
class DefaultSerialNumberFormatConfigConverter(
    private val serialNumberFormatPartSplitter: SerialNumberFormatPartSplitter,
    private val partConfigConverters: List<SerialNumberPartConfigConverter>,
) : SerialNumberFormatConfigConverter {

    /**
     * Converts a raw serial number format string into a [SerialNumberFormatConfig] instance.
     *
     * @param rawFormat the raw serial number format string to convert.
     * @return a [SerialNumberFormatConfig] instance that represents the raw serial number format string.
     *
     * @throws IllegalArgumentException if no [SerialNumberPartConfigConverter] is found for a part
     * of the raw format string.
     */
    override fun convert(rawFormat: String): SerialNumberFormatConfig {
        val partConfigs = serialNumberFormatPartSplitter.split(rawFormat).map { part ->
            partConfigConverters.find { it.isSuitable(part) }?.convert(part)
                ?: throw IllegalArgumentException("No SerialNumberPartConfigConverter found for part '$part'")
        }
        return SerialNumberFormatConfig(partConfigs)
    }
}
