package com.demo.canyouhearme.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.canyouhearme.common.helper.Constant.DIGIT_TIMER_DURATION
import com.demo.canyouhearme.common.helper.Constant.STOP_NOISE_TIMER_DURATION
import com.demo.canyouhearme.common.helper.getDigitFile
import com.demo.canyouhearme.common.helper.getNoiseFile
import com.demo.canyouhearme.common.helper.timer.Timer
import com.demo.canyouhearme.common.helper.toSeconds
import com.demo.canyouhearme.results.data.Result
import com.demo.canyouhearme.test.helper.TestRenderer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val timer: Timer,
    private val testRenderer: TestRenderer
): ViewModel() {

    private var _startTest = MutableSharedFlow<Int>()
    val startTest: SharedFlow<Int> = _startTest

    private var _playDigit = MutableSharedFlow<String>()
    val playDigit: SharedFlow<String> = _playDigit

    private var _playNoise = MutableSharedFlow<String>()
    val playNoise: SharedFlow<String> = _playNoise

    private var _stopNoise = MutableSharedFlow<Unit>()
    val stopNoise: SharedFlow<Unit> = _stopNoise

    private var _round = MutableStateFlow(1)
    val round: StateFlow<Int> = _round

    private var _result = MutableSharedFlow<Result>()
    val result: SharedFlow<Result> = _result

    private var roundCount = 0
    private var digitCount = 0

    init {
        nextTest()
    }

    private fun nextTest() {
        if (roundCount != 10) {
            viewModelScope.launch {
                _round.emit(++roundCount)
            }
            timer.start(
                onFinish = {
                    viewModelScope.launch {
                        _startTest.emit(0)
                        _playNoise.emit(testRenderer.getLevel().getNoiseFile())
                        updateDigit()
                    }
                },
                onTick = {
                    viewModelScope.launch {
                        _startTest.emit(it.toSeconds())
                    }
                }
            )
        } else viewModelScope.launch {
            _result.emit(testRenderer.getResult())
        }
    }

    fun nextDigit() {
        if (digitCount == 3) {
            digitCount = 0
            stopNoise()
        } else {
            timer.start(
                duration = DIGIT_TIMER_DURATION,
                onFinish = {
                    updateDigit()
                }
            )
        }
    }

    private fun stopNoise() = timer.start(
        duration = STOP_NOISE_TIMER_DURATION,
        onFinish = {
            viewModelScope.launch {
                _stopNoise.emit(Unit)
            }
        }
    )

    private fun updateDigit() = viewModelScope.launch {
        val digit = testRenderer.getTriplet()[digitCount].getDigitFile()
        _playDigit.emit(digit)
        digitCount++
    }

    fun submitRound(one: String, two: String, three: String) {
        testRenderer.checkRound(one.toInt(), two.toInt(), three.toInt())
        nextTest()
    }
}