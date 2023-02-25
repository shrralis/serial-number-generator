/*
 * The following code is created by shrralis.
 *
 *  - DecimalValueEncoder.kt (Last change: 2/24/23, 10:29 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.coder.encoder

/**
 * An interface for encoding [Long] decimal values to string.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
interface DecimalValueEncoder {

    val zero: Char

    /**
     * Encodes a decimal value to string.
     *
     * @param value the decimal value to encode.
     * @return the encoded string.
     */
    fun encode(value: Long): String

    fun isSuitable(radix: Int): Boolean
}
