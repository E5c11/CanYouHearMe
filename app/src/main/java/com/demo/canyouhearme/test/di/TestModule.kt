package com.demo.canyouhearme.test.di

import com.demo.canyouhearme.common.helper.timer.DefaultTimer
import com.demo.canyouhearme.common.helper.timer.Timer
import com.demo.canyouhearme.test.helper.TestRenderer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Random

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    fun providesTimer(): Timer = DefaultTimer()

    @Provides
    fun providesTestRenderer(random: Random): TestRenderer = TestRenderer(random)

    @Provides
    fun providesRandom(): Random = Random()
}