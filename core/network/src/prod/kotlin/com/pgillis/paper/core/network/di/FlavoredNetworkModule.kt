package com.pgillis.paper.core.network.di

import com.pgillis.paper.core.network.PaperNetworkDataSource
import com.pgillis.paper.core.network.retrofit.RetrofitPaperNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface FlavoredNetworkModule {

    @Binds
    fun binds(impl: RetrofitPaperNetwork): PaperNetworkDataSource
}