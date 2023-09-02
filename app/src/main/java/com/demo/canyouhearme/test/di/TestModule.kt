package com.demo.canyouhearme.test.di

import com.demo.canyouhearme.common.helper.DefaultTimer
import com.demo.canyouhearme.common.helper.Timer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TestModule {

    @Provides
    fun providesTimer(): Timer = DefaultTimer()
}