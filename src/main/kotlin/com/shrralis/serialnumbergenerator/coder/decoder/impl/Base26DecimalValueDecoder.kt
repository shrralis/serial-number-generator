/*
 * The following code is created by shrralis.
 *
 *  - Base26DecimalValueDecoder.kt (Last change: 2/24/23, 11:26 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.coder.decoder.impl

import com.shrralis.serialnumbergenerator.coder.decoder.DecimalValueDecoder
import com.shrralis.serialnumbergenerator.config.BASE_26_RADIX
import com.shrralis.serialnumbergenerator.config.DEFAULT_BASE_26_ZERO_CHAR
import java.util.concurrent.atomic.AtomicLong

/**
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
class Base26DecimalValueDecoder(
    override val zero: Char = DEFAULT_BASE_26_ZERO_CHAR,
) : DecimalValueDecoder {

    override fun decode(value: String): Long = AtomicLong(1).let { multiplier ->
        value.reversed().fold(0L) { acc, base26Digit ->
            (acc + ((base26Digit - zero) * multiplier.getAndMultiplyBy(BASE_26_RADIX)))
        }
    }

    override fun isSuitable(radix: Int): Boolean = radix == BASE_26_RADIX

    private fun AtomicLong.getAndMultiplyBy(value: Int) = getAndAccumulate(value.toLong()) { m, x -> m * x }
}
