package com.demo.canyouhearme.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var _result = MutableSharedFlow<List<Result>>()
    val result: SharedFlow<List<Result>> = _result

    fun getResults() = viewModelScope.launch {

    }

    fun uploadResults(result: Result) = viewModelScope.launch {

    }

}