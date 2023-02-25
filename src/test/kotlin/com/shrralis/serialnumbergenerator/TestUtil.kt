/*
 * The following code is created by shrralis.
 *
 *  - TestUtil.kt (Last change: 2/25/23, 4:50 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator

import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.params.aggregator.ArgumentsAccessor
import org.junit.jupiter.params.aggregator.ArgumentsAggregator

/**
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
fun convertBase10ToBase26(base10Value: Long, zero: Char = 'A'): String {
    var quotient = base10Value
    var remainder: Long
    var result = ""

    do {
        remainder = quotient % 26
        quotient /= 26
        result = (zero + remainder.toInt()) + result
    } while (quotient > 0)

    return result
}

class VarargsAggregator : ArgumentsAggregator {

    override fun aggregateArguments(accessor: ArgumentsAccessor, context: ParameterContext): Any = accessor.toList()
        .drop(context.index)
        .map { "$it" }
        .toTypedArray()
}
