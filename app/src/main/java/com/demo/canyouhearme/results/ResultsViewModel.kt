package com.demo.canyouhearme.results

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.canyouhearme.common.helper.Resource
import com.demo.canyouhearme.results.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val repo: ResultsRepository
): ViewModel() {

    private var _result = MutableSharedFlow<Resource<List<Result>>>()
    val result: SharedFlow<Resource<List<Result>>> = _result

    fun getResults() = viewModelScope.launch {
        _result.emit(Resource.loading())
        repo.observe().collect {
            _result.emit(it)
        }
    }

    fun uploadResults(result: Result) = viewModelScope.launch {
        repo.uploadAndObserve(result).collect {
            Log.d("myT", "uploadResults: ${it.error}")
            _result.emit(it)
        }
    }

}