package com.demo.canyouhearme.results.data.exception

data class ResultUploadException(
    val msg: String = "Result upload failed",
    val error: Throwable? = null
): Exception(msg, error)
