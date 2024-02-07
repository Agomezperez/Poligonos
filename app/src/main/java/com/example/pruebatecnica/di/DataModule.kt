package com.example.pruebatecnica.di

import com.example.pruebatecnica.api.services.SyncRetrofitService
import com.example.pruebatecnica.data.local.database.DatabaseInstance
import com.example.pruebatecnica.data.local.database.repository.FiguraRepo
import com.example.pruebatecnica.data.local.repository.FiguraRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun figureRepoProvider(
        databaseInstance: DatabaseInstance,
    ): FiguraRepo = FiguraRepoImpl(databaseInstance.figuraDao())
}