package com.demo.canyouhearme.results.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Result(
    var score: Int = 0,
    var rounds: @RawValue ArrayList<Round> = arrayListOf(),
    var date: String = ""
): Parcelable
