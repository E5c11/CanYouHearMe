package com.demo.canyouhearme.results.data

import com.google.gson.annotations.SerializedName

data class Round(
    @SerializedName("difficulty"       ) var difficulty      : Int,
    @SerializedName("triplet_played"   ) var tripletPlayed   : String,
    @SerializedName("triplet_answered" ) var tripletAnswered : String
)
