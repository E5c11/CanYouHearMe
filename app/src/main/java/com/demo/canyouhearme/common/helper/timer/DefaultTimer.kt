package com.demo.canyouhearme.common.helper.timer

import android.os.CountDownTimer

class DefaultTimer : Timer {

    private lateinit var timer: CountDownTimer

    override fun start(duration: Long, interval: Long, onFinish: () -> Unit, onTick: (Long) -> Unit) {
        timer = object : CountDownTimer(duration, interval) {
            override fun onTick(millisUntilFinished: Long) {
                onTick(millisUntilFinished)
            }

            override fun onFinish() {
                onFinish()
            }
        }.start()
    }

    override fun cancel() {
        if (this::timer.isInitialized) timer.cancel()
    }
}