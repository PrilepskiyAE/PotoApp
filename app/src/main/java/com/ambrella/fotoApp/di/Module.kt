package com.ambrella.fotoApp.di

import com.ambrella.fotoApp.data.YandexApi
import com.ambrella.fotoApp.data.YandexApiRepository
import com.ambrella.fotoApp.data.YandexApiRepositoryImpl
import com.ambrella.fotoApp.utils.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofitModule(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://cloud-api.yandex.net/v1/disk/resources/")
            .addConverterFactory(GsonConverterFactory.create())
            .apply {
                client(
                    OkHttpClient.Builder()
                        .addInterceptor(HeaderInterceptor())
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                        )
                        .readTimeout(1, TimeUnit.MINUTES)
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(1, TimeUnit.MINUTES)
                        .build()
                )
            }
            .build()
    @Provides
    fun provideApiModule(retrofit: Retrofit): YandexApi =retrofit.create(YandexApi::class.java)
    @Provides
    fun getYandexApiRepository(data: YandexApi): YandexApiRepository = YandexApiRepositoryImpl(data)
}