/*
 * The following code is created by shrralis.
 *
 *  - SerialNumberFormatConfig.kt (Last change: 2/25/23, 6:08 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.config.format

import com.shrralis.serialnumbergenerator.config.format.part.AbstractGeneratedSerialNumberPartConfig
import com.shrralis.serialnumbergenerator.config.format.part.SerialNumberPartConfig

/**
 * A configuration data class for defining the format of a serial number.
 *
 * @property partConfigs a list of [SerialNumberPartConfig] instances that define the parts of the serial number.
 * @property serialNumberLength a total length of the serial number, calculated as the sum of the widths of all parts.
 * @property maxDecimalValue a maximum possible decimal value of the serial number for the config.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
data class SerialNumberFormatConfig(val partConfigs: List<SerialNumberPartConfig>) {

    val serialNumberLength: Int by lazy { partConfigs.sumOf { it.width } }

    val maxDecimalValue: Long = partConfigs.filterIsInstance<AbstractGeneratedSerialNumberPartConfig>()
        .map { it.maxDecimalValue }
        .fold(1L) { acc, maxPartDecimalValue -> acc * maxPartDecimalValue.inc() }
        .dec()
}
