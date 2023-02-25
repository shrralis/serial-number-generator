/*
 * The following code is created by shrralis.
 *
 *  - StaticSerialNumberPartConfigConverter.kt (Last change: 2/24/23, 11:39 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.converter.format.part.impl.statics

import com.shrralis.serialnumbergenerator.config.format.part.SerialNumberPartConfig
import com.shrralis.serialnumbergenerator.config.format.part.impl.static.StaticSerialNumberPartConfig
import com.shrralis.serialnumbergenerator.converter.format.part.SerialNumberPartConfigConverter

/**
 * A converter class for a [StaticSerialNumberPartConfig].
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
class StaticSerialNumberPartConfigConverter : SerialNumberPartConfigConverter {

    override fun convert(rawFormatPart: String): SerialNumberPartConfig = StaticSerialNumberPartConfig(rawFormatPart)

    override fun isSuitable(rawFormatPart: String): Boolean = rawFormatPart.isNotEmpty()
}
