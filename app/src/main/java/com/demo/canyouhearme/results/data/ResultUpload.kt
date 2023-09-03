package com.demo.canyouhearme.results.data

import com.google.gson.annotations.SerializedName

data class ResultUpload(
    @SerializedName("score"  ) val score  : Int,
    @SerializedName("rounds" ) val rounds : ArrayList<Round>
)
