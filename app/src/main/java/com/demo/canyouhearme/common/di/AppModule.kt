package com.demo.canyouhearme.common.di

import android.app.Application
import androidx.room.Room
import com.demo.canyouhearme.common.helper.DispatcherProvider
import com.demo.canyouhearme.common.helper.media.DefaultMediaPlayer
import com.demo.canyouhearme.common.helper.media.MediaPlayer
import com.demo.canyouhearme.results.io.local.ResultDao
import com.demo.canyouhearme.results.io.local.ResultDatabase
import com.demo.canyouhearme.results.io.local.ResultDatabase.Companion.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(app: Application) =
        Room.databaseBuilder(app, ResultDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesResultDao(db: ResultDatabase): ResultDao = db.resultDao()

    @Provides
    @Singleton
    fun providesMediaPlayer(): MediaPlayer = DefaultMediaPlayer()

    @Provides
    @Singleton
    fun providesDispatchProvider() = DispatcherProvider()
}