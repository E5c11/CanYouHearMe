package com.demo.canyouhearme.results.io

import com.demo.canyouhearme.results.data.Result
import kotlinx.coroutines.flow.Flow

interface ResultDataSource {
    fun insert(result: Result)
    fun delete(result: Result)
    fun fetchById(id: Int)
    fun fetchAll(): Flow<List<Result>>
}