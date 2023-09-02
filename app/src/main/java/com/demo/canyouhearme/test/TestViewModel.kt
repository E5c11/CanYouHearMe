package com.demo.canyouhearme.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.canyouhearme.common.helper.Timer
import com.demo.canyouhearme.common.helper.toSeconds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val timer: Timer
): ViewModel() {

    private var _startTest = MutableSharedFlow<Int>()
    val startTest: SharedFlow<Int> = _startTest

    private val testCount = 1

    init {
        startTest()
    }

    private fun startTest() {
        timer.start(
            onFinish = {
                viewModelScope.launch {
                    _startTest.emit(0)
                }
            },
            onTick = {
                viewModelScope.launch {
                    _startTest.emit(it.toSeconds())
                }
            }
        )
    }
}