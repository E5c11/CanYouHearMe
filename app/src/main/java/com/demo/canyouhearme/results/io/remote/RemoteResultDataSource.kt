package com.demo.canyouhearme.results.io.remote

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
    override suspend fun insert(result: Result): Resource<String> {
        return try {
            val response = api.postResults(result.toUpload())
            val body = response.body()
            if (response.isSuccessful) Resource.success("")
            else Resource.error(ResultUploadException())
        } catch (e: Exception) {
            Resource.error(ResultUploadException(error = e))
        }
    }

    override suspend fun delete(result: Result): Resource<String> {
        throw CannotDeleteRemoteException()
    }

    override fun fetchAll(): Flow<List<Result>> {
        throw CannotFetchRemoteException()
    }
}