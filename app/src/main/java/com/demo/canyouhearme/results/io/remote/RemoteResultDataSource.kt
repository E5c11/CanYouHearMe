package com.demo.canyouhearme.results.io.remote

import com.demo.canyouhearme.common.helper.Resource
import com.demo.canyouhearme.results.data.Result
import com.demo.canyouhearme.results.data.exception.CannotDeleteRemoteException
import com.demo.canyouhearme.results.data.exception.CannotFetchRemoteException
import com.demo.canyouhearme.results.data.exception.ResultUploadException
import com.demo.canyouhearme.results.data.toUpload
import com.demo.canyouhearme.results.io.ResultDataSource
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteResultDataSource(
    private val api: ResultApi
): ResultDataSource {
    override suspend fun insert(result: Result): Resource<String> = suspendCoroutine {
        try {
            val response = api.postResults(result.toUpload())
            val body = response.body()
            if (response.isSuccessful) it.resume(Resource.success(""))
            else it.resume(Resource.error(ResultUploadException()))
        } catch (e: Exception) {
            it.resume(Resource.error(ResultUploadException(error = e)))
        }
    }

    override suspend fun delete(result: Result): Resource<String> {
        throw CannotDeleteRemoteException()
    }

    override suspend fun fetchById(id: Int): Resource<Result> {
        throw CannotFetchRemoteException()
    }

    override fun fetchAll(): Flow<List<Result>> {
        throw CannotFetchRemoteException()
    }
}