package com.nexuzy.hypechats.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.nexuzy.hypechats.data.api.RetrofitClient
import com.nexuzy.hypechats.data.api.HypechatsApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideHypechatsApiService(): HypechatsApiService {
        return RetrofitClient.create(HypechatsApiService::class.java)
    }
}
