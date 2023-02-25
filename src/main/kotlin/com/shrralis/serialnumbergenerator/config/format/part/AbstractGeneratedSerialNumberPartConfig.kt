/*
 * The following code is created by shrralis.
 *
 *  - AbstractGeneratedSerialNumberPartConfig.kt (Last change: 2/25/23, 6:06 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.config.format.part

/**
 * An abstract base class for generated serial number parts.
 *
 * @property width a width of the generated serial number part.
 * @property radix the radix of the generated serial number part.
 * @property maxDecimalValue the maximum possible decimal value of the generated serial number part.
 *
 * @throws IllegalArgumentException if the width is not a positive value.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
abstract class AbstractGeneratedSerialNumberPartConfig(final override val width: Int) : SerialNumberPartConfig {

    abstract val radix: Int
    val maxDecimalValue: Long by lazy { (1..width).fold(1L) { acc, _ -> acc * radix }.dec() }

    init {
        require(width > 0) { "Width must be positive value" }
    }
}
