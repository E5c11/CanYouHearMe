package com.demo.canyouhearme.results.data

data class Result(
    var score: Int = 0,
    var rounds: ArrayList<Round> = arrayListOf(),
    var date: String = ""
)
