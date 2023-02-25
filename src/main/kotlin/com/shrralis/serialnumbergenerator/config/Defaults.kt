/*
 * The following code is created by shrralis.
 *
 *  - Defaults.kt (Last change: 2/24/23, 10:29 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.config

/**
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
const val BASE_10_RADIX = 10
const val BASE_26_RADIX = 26

const val DEFAULT_GENERATED_PART_PATTERN = "[A0][{]\\d+}"
const val DEFAULT_GENERATED_PART_WIDTH_PATTERN = "(?<=[{])\\d+(?=})"

const val DEFAULT_BASE_10_PART_PATTERN = "0[{]\\d+}"
const val DEFAULT_BASE_10_ZERO_CHAR = '0'

const val DEFAULT_BASE_26_PART_PATTERN = "A[{]\\d+}"
const val DEFAULT_BASE_26_ZERO_CHAR = 'A'
