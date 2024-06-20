package com.example.poligonos.di

import com.example.poligonos.data.local.database.DatabaseInstance
import com.example.poligonos.data.local.database.repository.FiguraRepo
import com.example.poligonos.data.local.repository.FiguraRepoImpl
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