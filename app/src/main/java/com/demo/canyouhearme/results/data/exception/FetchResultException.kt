package com.demo.canyouhearme.results.data.exception

data class FetchResultException(
    val msg: String = "Results fetch failed",
    val error: Throwable? = null
): Exception()
