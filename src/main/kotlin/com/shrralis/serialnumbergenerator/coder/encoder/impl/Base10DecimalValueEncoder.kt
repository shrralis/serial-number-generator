/*
 * The following code is created by shrralis.
 *
 *  - Base10DecimalValueEncoder.kt (Last change: 2/24/23, 10:29 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.coder.encoder.impl

import com.shrralis.serialnumbergenerator.coder.encoder.DecimalValueEncoder
import com.shrralis.serialnumbergenerator.config.BASE_10_RADIX
import com.shrralis.serialnumbergenerator.config.DEFAULT_BASE_10_ZERO_CHAR

/**
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
class Base10DecimalValueEncoder(
    override val zero: Char = DEFAULT_BASE_10_ZERO_CHAR,
) : DecimalValueEncoder {

    override fun encode(value: Long): String = "$value"

    override fun isSuitable(radix: Int): Boolean = radix == BASE_10_RADIX
}
