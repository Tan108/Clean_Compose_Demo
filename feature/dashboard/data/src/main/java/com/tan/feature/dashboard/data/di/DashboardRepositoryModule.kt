package com.tan.feature.dashboard.data.di

import com.tan.feature.dashboard.data.api.DashboardApiService
import com.tan.feature.dashboard.data.repository.DashboardRepositoryImpl
import com.tan.feature.dashboard.domain.repository.DashboardRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
abstract class DashboardRepositoryModule {

    @Binds
    abstract fun bindsDashboardRepository(dashboardRepositoryImpl: DashboardRepositoryImpl): DashboardRepository

    @Provides
    fun provideDashboardApi(
        retrofit: Retrofit
    ):DashboardApiService = retrofit.create(DashboardApiService::class.java)
}