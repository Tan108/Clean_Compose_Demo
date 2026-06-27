package com.tan.core.network.di

import com.tan.core.network.interceptors.HeaderInterceptors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideHeaderInterceptor() = HeaderInterceptors()

    @Provides
    fun provideOkHttpClient(
        headerInterceptors: HeaderInterceptors
    ) =
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptors)
            .build()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.sportmonks.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}