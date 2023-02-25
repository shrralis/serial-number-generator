/*
 * The following code is created by shrralis.
 *
 *  - StaticSerialNumberPartConfig.kt (Last change: 2/24/23, 11:39 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.config.format.part.impl.static

import com.shrralis.serialnumbergenerator.config.format.part.SerialNumberPartConfig

/**
 * A configuration class for a static serial number part that has a fixed value.
 *
 * @property value the value of the static serial number part.
 *
 * @throws IllegalArgumentException if the value is blank.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
data class StaticSerialNumberPartConfig(val value: String) : SerialNumberPartConfig {

    override val width: Int = value.length

    init {
        require(width > 0) { "Value should not be empty" }
    }
}
