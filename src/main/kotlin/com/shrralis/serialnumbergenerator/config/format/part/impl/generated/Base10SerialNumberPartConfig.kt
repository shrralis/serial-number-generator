/*
 * The following code is created by shrralis.
 *
 *  - Base10SerialNumberPartConfig.kt (Last change: 2/24/23, 9:50 PM by shrralis)
 *
 * Copyright (c) 2023-2023 by shrralis.
 */

package com.shrralis.serialnumbergenerator.config.format.part.impl.generated

import com.shrralis.serialnumbergenerator.config.BASE_10_RADIX
import com.shrralis.serialnumbergenerator.config.format.part.AbstractGeneratedSerialNumberPartConfig

/**
 * A concrete implementation of the GeneratedSerialNumberPartConfig abstract class,
 * which generates serial number parts using the base-10 number system.
 *
 * @property width the width of the base-10 serial number part.
 *
 * @author shrralis
 * @see <a href="https://t.me/Shrralis">https://t.me/Shrralis</a>
 */
class Base10SerialNumberPartConfig(width: Int) : AbstractGeneratedSerialNumberPartConfig(width) {

    override val radix: Int = BASE_10_RADIX
}
