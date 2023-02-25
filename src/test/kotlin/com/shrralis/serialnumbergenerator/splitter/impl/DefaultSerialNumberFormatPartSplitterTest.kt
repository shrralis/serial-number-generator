/*
 * The following code is created by shrralis.
 *
 *  - DefaultSerialNumberFormatPartSplitterTest.kt (Last change: 2/25/23, 4:57 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.splitter.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

/**
 * @author shrralis
 * @see [https://t.me/Shrralis](https://t.me/Shrralis)
 */
class DefaultSerialNumberFormatPartSplitterTest {

    private val instance = DefaultSerialNumberFormatPartSplitter()

    @ParameterizedTest
    @MethodSource("getData")
    fun `splits parts properly`(rawFormat: String, vararg expectedParts: String) {
        assertThat(instance.split(rawFormat)).contains(*expectedParts)
    }

    companion object {

        @JvmStatic
        private fun getData(): List<Arguments> = listOf(
            arguments("A{1}", arrayOf("A{1}")),
            arguments("A{154}", arrayOf("A{154}")),
            arguments("0{2}", arrayOf("0{2}")),
            arguments("0{35}", arrayOf("0{35}")),
            arguments("AA{1}", arrayOf("A", "A{1}")),
            arguments("ZA{154}", arrayOf("Z", "A{154}")),
            arguments("00{2}", arrayOf("0", "0{2}")),
            arguments("90{35}", arrayOf("9", "0{35}")),
            arguments("AA{1}1", arrayOf("A", "A{1}", "1")),
            arguments("ZA{154}Z", arrayOf("Z", "A{154}", "Z")),
            arguments("00{2}13", arrayOf("0", "0{2}", "13")),
            arguments("90{35}Z0", arrayOf("9", "0{35}", "Z0")),
        )
    }
}
