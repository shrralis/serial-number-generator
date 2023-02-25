/*
 * The following code is created by shrralis.
 *
 *  - Base26DecimalValueEncoder.kt (Last change: 2/24/23, 10:29 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.coder.encoder.impl

import com.shrralis.serialnumbergenerator.coder.encoder.DecimalValueEncoder
import com.shrralis.serialnumbergenerator.config.BASE_26_RADIX
import com.shrralis.serialnumbergenerator.config.DEFAULT_BASE_26_ZERO_CHAR

/**
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
class Base26DecimalValueEncoder(
    override val zero: Char = DEFAULT_BASE_26_ZERO_CHAR,
) : DecimalValueEncoder {

    override fun encode(value: Long): String {
        require(value >= 0) { "Only positive numbers or 0 are allowed" }

        var encoded = ""
        var remaining = value

        do {
            encoded = "${zero + (remaining % BASE_26_RADIX).toInt()}$encoded"
            remaining /= BASE_26_RADIX
        } while (remaining > 0)

        return encoded
    }

    override fun isSuitable(radix: Int): Boolean = radix == BASE_26_RADIX
}
