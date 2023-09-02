package com.demo.canyouhearme.common.helper

import com.demo.canyouhearme.common.helper.Constant.TIMER_DURATION
import com.demo.canyouhearme.common.helper.Constant.TIMER_INTERVAL

interface Timer {
    fun start(
        duration: Long = TIMER_DURATION,
        interval: Long = TIMER_INTERVAL,
        onFinish: () -> Unit,
        onTick: (Long) -> Unit = {}
    )
    fun cancel()
}