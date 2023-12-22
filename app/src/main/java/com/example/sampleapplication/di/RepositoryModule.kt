package com.example.sampleapplication.di

import com.example.sampleapplication.repo.SampleEntityRepository
import com.example.sampleapplication.repo.SampleEntityRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun sampleEntities(repository: SampleEntityRepositoryImpl): SampleEntityRepository
}