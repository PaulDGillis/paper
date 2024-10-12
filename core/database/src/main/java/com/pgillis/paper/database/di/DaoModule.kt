package com.pgillis.paper.database.di

import com.pgillis.paper.database.PaperDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun provideItemDao(paperDatabase: PaperDatabase) = paperDatabase.itemDao()
}