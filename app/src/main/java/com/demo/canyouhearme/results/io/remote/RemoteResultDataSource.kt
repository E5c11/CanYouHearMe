package com.demo.canyouhearme.results.io.remote

import android.util.Log
import com.demo.canyouhearme.common.helper.Resource
import com.demo.canyouhearme.results.data.Result
import com.demo.canyouhearme.results.data.exception.CannotDeleteRemoteException
import com.demo.canyouhearme.results.data.exception.CannotFetchRemoteException
import com.demo.canyouhearme.results.data.exception.ResultUploadException
import com.demo.canyouhearme.results.data.toUpload
import com.demo.canyouhearme.results.io.ResultDataSource
import kotlinx.coroutines.flow.Flow

class RemoteResultDataSource(
    private val api: ResultApi
): ResultDataSource {
    override suspend fun insert(result: Result) {
        try {
            val response = api.postResults(result.toUpload())
            val body = response.body()
            Log.d("myT", "insert: ${response.code()}")
            if (!response.isSuccessful) throw ResultUploadException()
        } catch (e: Exception) {
            throw ResultUploadException(error = e)
        }
    }

    override suspend fun delete(result: Result) {
        throw CannotDeleteRemoteException()
    }

    override fun fetchAll(): Flow<Resource<List<Result>>> {
        throw CannotFetchRemoteException()
    }
}