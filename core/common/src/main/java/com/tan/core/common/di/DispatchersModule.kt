package com.tan.core.common.di

import com.tan.core.common.annotations.DefaultDispatcher
import com.tan.core.common.annotations.IoDispatcher
import com.tan.core.common.annotations.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object DispatchersModule {

    @IoDispatcher
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher() = Dispatchers.Default

    @MainDispatcher
    @Provides
    fun provideMainDispatcher() = Dispatchers.Main

}