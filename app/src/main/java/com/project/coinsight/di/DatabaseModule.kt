package com.project.coinsight.di

import android.content.Context
import com.project.coinsight.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.room.Room

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "coin_database"
    )
        .fallbackToDestructiveMigration(true)
        .build()

    @Singleton
    @Provides
    fun provideCoinDao(appDatabase: AppDatabase) = appDatabase.coinDao()

}