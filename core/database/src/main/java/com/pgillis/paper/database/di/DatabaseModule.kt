package com.pgillis.paper.database.di

import android.content.Context
import androidx.room.Room
import com.pgillis.paper.database.PaperDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun bindDatabase(
        @ApplicationContext appContext: Context
    ): PaperDatabase = Room.databaseBuilder(appContext, PaperDatabase::class.java, "paper-db").build()
}