package com.demo.canyouhearme.results.data.exception

data class CannotDeleteRemoteException(
    val msg: String = "Cannot delete remote result",
    val error: Throwable? = null
): Exception(msg, error)
