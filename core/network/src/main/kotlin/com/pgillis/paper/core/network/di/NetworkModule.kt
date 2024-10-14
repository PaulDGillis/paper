package com.pgillis.paper.core.network.di

import android.content.Context
import androidx.tracing.trace
import com.pgillis.paper.core.network.BuildConfig
import com.pgillis.paper.core.network.demo.DemoAssetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun provideJson() = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideDemoAssetManager(@ApplicationContext context: Context) = DemoAssetManager(context.assets::open)

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()
}