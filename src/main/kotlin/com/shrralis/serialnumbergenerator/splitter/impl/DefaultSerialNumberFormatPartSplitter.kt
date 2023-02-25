/*
 * The following code is created by shrralis.
 *
 *  - DefaultSerialNumberFormatPartSplitter.kt (Last change: 2/24/23, 9:54 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.splitter.impl

import com.shrralis.serialnumbergenerator.config.DEFAULT_GENERATED_PART_PATTERN
import com.shrralis.serialnumbergenerator.splitter.SerialNumberFormatPartSplitter

/**
 * The default implementation of the [SerialNumberFormatPartSplitter] interface.
 * It uses a regular expression pattern to split the raw serial number format string.
 *
 * @param generatedRawFormatPartRegex the regular expression pattern used to identify the generated serial number parts.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
class DefaultSerialNumberFormatPartSplitter(
    generatedRawFormatPartRegex: Regex = Regex(DEFAULT_GENERATED_PART_PATTERN),
) : SerialNumberFormatPartSplitter {

    /**
     * The regular expression pattern used to split the serial number format string into individual parts.
     */
    private val partsSplitRegex: Regex = Regex("(?<=$generatedRawFormatPartRegex)|(?=$generatedRawFormatPartRegex)")

    /**
     * Splits the given [rawFormat] string into its individual parts using the [partsSplitRegex] pattern.
     *
     * @param rawFormat the serial number format string to split.
     * @return the list of individual parts from the serial number format string.
     */
    override fun split(rawFormat: String): List<String> = partsSplitRegex.split(rawFormat).filterNot(String::isEmpty)
}
