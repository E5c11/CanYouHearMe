package com.demo.canyouhearme.results.data.exception

data class InsertResultException(
    val msg: String = "Result insert failed",
    val error: Throwable? = null
): Exception()
