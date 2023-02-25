/*
 * The following code is created by shrralis.
 *
 *  - SerialNumberParser.kt (Last change: 2/24/23, 10:02 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator

/**
 * A serial number parser that can be used to parse serial numbers.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
interface SerialNumberParser {

    /**
     * Parses the given serial number and returns the decimal value it represents.
     *
     * @param serialNumber the serial number to parse.
     * @return the decimal value represented by the serial number.
     */
    fun parse(serialNumber: String): Long
}
