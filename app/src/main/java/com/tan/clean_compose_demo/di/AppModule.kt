package com.tan.clean_compose_demo.di

import android.content.Context
import com.tan.clean_compose_demo.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext applicationContext: Context) = AppDatabase.getInstance(applicationContext)

    @Provides
    fun provideDashboardDao(appDatabase: AppDatabase) = appDatabase.getDashDao()

}