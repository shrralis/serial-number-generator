/*
 * The following code is created by shrralis.
 *
 *  - Base10DecimalValueDecoder.kt (Last change: 2/24/23, 11:03 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.coder.decoder.impl

import com.shrralis.serialnumbergenerator.coder.decoder.DecimalValueDecoder
import com.shrralis.serialnumbergenerator.config.BASE_10_RADIX
import com.shrralis.serialnumbergenerator.config.DEFAULT_BASE_10_ZERO_CHAR

/**
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
class Base10DecimalValueDecoder(
    override val zero: Char = DEFAULT_BASE_10_ZERO_CHAR,
) : DecimalValueDecoder {

    override fun decode(value: String): Long = value.toLong()

    override fun isSuitable(radix: Int): Boolean = radix == BASE_10_RADIX
}
