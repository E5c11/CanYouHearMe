package com.demo.canyouhearme.common.helper.timer

import com.demo.canyouhearme.common.helper.Constant.DEFAULT_TIMER_DURATION
import com.demo.canyouhearme.common.helper.Constant.DEFAULT_TIMER_INTERVAL

interface Timer {
    fun start(
        duration: Long = DEFAULT_TIMER_DURATION,
        interval: Long = DEFAULT_TIMER_INTERVAL,
        onFinish: () -> Unit,
        onTick: (Long) -> Unit = {}
    )
    fun cancel()
}