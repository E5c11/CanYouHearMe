package com.demo.canyouhearme.results.io.local

import com.demo.canyouhearme.common.helper.DispatcherProvider
import com.demo.canyouhearme.common.helper.Resource
import com.demo.canyouhearme.results.data.Result
import com.demo.canyouhearme.results.data.exception.DeleteResultException
import com.demo.canyouhearme.results.data.exception.FetchResultException
import com.demo.canyouhearme.results.data.exception.InsertResultException
import com.demo.canyouhearme.results.data.toEntity
import com.demo.canyouhearme.results.data.toResultList
import com.demo.canyouhearme.results.io.ResultDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LocalResultDataSource(
    private val dao: ResultDao,
    private val dispatcher: DispatcherProvider
): ResultDataSource {
    override suspend fun insert(result: Result): Resource<String> {
        return try {
            dao.insert(result.toEntity())
            Resource.success("")
        } catch (e: Exception) {
            Resource.error(InsertResultException(error = e))
        }
    }

    override suspend fun delete(result: Result): Resource<String> {
        return try {
            val response = dao.delete(result.toEntity())
            if (response == 0) Resource.error(DeleteResultException())
            else Resource.success("")
        } catch (e: Exception) {
            Resource.error(DeleteResultException(error = e))
        }
    }

    override fun fetchAll(): Flow<Resource<List<Result>>> {
        return try {
            dao.fetchAll().map {
                Resource.success(it.toResultList())
            }
        } catch (e: Exception) {
            flow { emit(Resource.error(FetchResultException(error = e))) }
        }.flowOn(dispatcher.io)
    }
}