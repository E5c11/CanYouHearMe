package com.demo.canyouhearme.common.helper

import kotlin.math.ceil

fun Long.toSeconds() = ceil(this.toDouble() / 1000.0).toInt()