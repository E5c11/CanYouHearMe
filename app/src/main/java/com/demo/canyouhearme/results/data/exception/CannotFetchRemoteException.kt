package com.demo.canyouhearme.results.data.exception

data class CannotFetchRemoteException(
    val msg: String = "Cannot fetch from remote",
    val error: Throwable? = null
): Exception(msg, error)
