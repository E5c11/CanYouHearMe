package com.demo.canyouhearme.results

import com.demo.canyouhearme.common.helper.Resource
import com.demo.canyouhearme.results.data.Result
import com.demo.canyouhearme.results.data.exception.DeleteResultException
import com.demo.canyouhearme.results.data.exception.InsertResultException
import com.demo.canyouhearme.results.io.ResultDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ResultsRepository(
    private val local: ResultDataSource,
    private val remote: ResultDataSource
) {

    fun uploadAndObserve(result: Result): Flow<Resource<List<Result>>> = flow {
        emit(Resource.loading())
        try {
            remote.insert(result)
            local.insert(result)
            local.fetchAll().collect {
                emit(it)
            }
        } catch (e: Exception) {
            emit(Resource.error(InsertResultException(error = e)))
        }
    }

    fun delete(result: Result): Flow<Resource<String>> = flow {
        emit(Resource.loading())
        try {
            local.delete(result)
            emit(Resource.success(""))
        } catch (e: Exception) {
            emit(Resource.error(error = DeleteResultException()))
        }
    }

    fun observe() = local.fetchAll()
}