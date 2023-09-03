package com.demo.canyouhearme.results.di

import com.demo.canyouhearme.common.helper.Constant.BASE_URL
import com.demo.canyouhearme.common.helper.DispatcherProvider
import com.demo.canyouhearme.results.ResultsRepository
import com.demo.canyouhearme.results.io.ResultDataSource
import com.demo.canyouhearme.results.io.local.LocalResultDataSource
import com.demo.canyouhearme.results.io.local.ResultDao
import com.demo.canyouhearme.results.io.remote.RemoteResultDataSource
import com.demo.canyouhearme.results.io.remote.ResultApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteSource

@Module
@InstallIn(ViewModelComponent::class)
object ResultsModule {

    @Provides
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun providesWeatherApi(client: OkHttpClient): ResultApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(ResultApi::class.java)

    @Provides
    @RemoteSource
    fun providesRemoteDataSource(api: ResultApi): ResultDataSource = RemoteResultDataSource(api)

    @Provides
    @LocalSource
    fun providesLocalDataSource(dao: ResultDao, dispatcher: DispatcherProvider): ResultDataSource =
        LocalResultDataSource(dao, dispatcher)

    @Provides
    fun providesResultsRepository(local: ResultDataSource, remote: ResultDataSource) =
        ResultsRepository(local, remote)

}