package com.demo.canyouhearme.results.io.remote

import com.demo.canyouhearme.results.data.Result
import com.demo.canyouhearme.results.io.ResultDataSource
import kotlinx.coroutines.flow.Flow

class RemoteResultDataSource(
    private val api: ResultApi
): ResultDataSource {
    override fun insert(result: Result) {
        TODO("Not yet implemented")
    }

    override fun delete(result: Result) {
        TODO("Not yet implemented")
    }

    override fun fetchById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun fetchAll(): Flow<List<Result>> {
        TODO("Not yet implemented")
    }
}