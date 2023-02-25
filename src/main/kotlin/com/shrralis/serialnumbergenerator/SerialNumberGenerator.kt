/*
 * The following code is created by shrralis.
 *
 *  - SerialNumberGenerator.kt (Last change: 2/24/23, 10:01 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator

/**
 * A serial number generator that can be used to generate serial numbers.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
interface SerialNumberGenerator {

    /**
     * Generates a serial number for the given decimal value.
     *
     * @param decimalValue the decimal value to generate a serial number for.
     * @return the generated serial number as a string.
     */
    fun generate(decimalValue: Long): String
}
