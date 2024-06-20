package com.example.poligonos.di

import android.content.Context
import com.example.poligonos.data.local.database.DatabaseInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun databaseInstance(
        @ApplicationContext context: Context
    ): DatabaseInstance = DatabaseInstance.getInstance(context)
}