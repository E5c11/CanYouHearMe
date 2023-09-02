package com.demo.canyouhearme.common.helper

import android.content.Context
import com.demo.canyouhearme.R

fun Int.getDigitFile() = when (this) {
    1 -> "digit/1.m4a"
    2 -> "digit/2.m4a"
    3 -> "digit/3.m4a"
    4 -> "digit/4.m4a"
    5 -> "digit/5.m4a"
    6 -> "digit/6.m4a"
    7 -> "digit/7.m4a"
    8 -> "digit/8.m4a"
    else -> "digit/9.m4a"
}

fun Int.getNoiseFile() = when (this) {
    1 -> "noise/noise_1.m4a"
    2 -> "noise/noise_2.m4a"
    3 -> "noise/noise_3.m4a"
    4 -> "noise/noise_4.m4a"
    5 -> "noise/noise_5.m4a"
    6 -> "noise/noise_6.m4a"
    7 -> "noise/noise_7.m4a"
    8 -> "noise/noise_8.m4a"
    9 -> "noise/noise_9.m4a"
    else -> "noise/noise_10.m4a"
}
