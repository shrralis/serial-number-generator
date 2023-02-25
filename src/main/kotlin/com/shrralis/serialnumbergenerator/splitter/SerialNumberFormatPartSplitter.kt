/*
 * The following code is created by shrralis.
 *
 *  - SerialNumberFormatPartSplitter.kt (Last change: 2/24/23, 9:52 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.splitter

/**
 * An interface for splitting a raw serial number format string into its individual parts.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
interface SerialNumberFormatPartSplitter {

    /**
     * Splits the given [rawFormat] string into its individual parts.
     *
     * @param rawFormat the serial number format string to split.
     * @return the list of individual parts from the serial number format string.
     */
    fun split(rawFormat: String): List<String>
}
