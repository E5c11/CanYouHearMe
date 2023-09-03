package com.demo.canyouhearme.results.data.exception

data class DeleteResultException(
    val msg: String = "Result delete failed",
    val error: Throwable? = null
): Exception(msg, error)
