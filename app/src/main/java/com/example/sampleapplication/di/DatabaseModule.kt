package com.example.sampleapplication.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.sampleapplication.db.SampleApplicationDatabase
import com.example.sampleapplication.db.dao.SampleEntityDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideSampleEntityDao(appDatabase: SampleApplicationDatabase): SampleEntityDao {
        return appDatabase.sampleEntityDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): SampleApplicationDatabase {
        return Room.databaseBuilder(
            appContext,
            SampleApplicationDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    companion object {
        private const val DATABASE_NAME = "sample.db"
    }
}