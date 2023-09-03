package com.demo.canyouhearme.results.io

import com.demo.canyouhearme.common.helper.Resource
import com.demo.canyouhearme.results.data.Result
import kotlinx.coroutines.flow.Flow

interface ResultDataSource {
    suspend fun insert(result: Result)
    suspend fun delete(result: Result)
    fun fetchAll(): Flow<Resource<List<Result>>>
}