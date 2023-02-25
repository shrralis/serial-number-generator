/*
 * The following code is created by shrralis.
 *
 *  - DecimalValueDecoder.kt (Last change: 2/24/23, 11:03 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.coder.decoder

/**
 * An interface for decoding string number values to [Long].
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
interface DecimalValueDecoder {

    val zero: Char

    /**
     * Decodes a string number value to [Long].
     *
     * @param value the string number value to decode.
     * @return the decoded decimal value.
     */
    fun decode(value: String): Long

    fun isSuitable(radix: Int): Boolean
}
