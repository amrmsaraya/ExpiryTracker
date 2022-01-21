package com.github.amrmsaraya.expirytracker.di

import com.github.amrmsaraya.expirytracker.data.local.ProductDao
import com.github.amrmsaraya.expirytracker.data.source.LocalDataSource
import com.github.amrmsaraya.expirytracker.data.sourceImpl.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSource {

    @Provides
    @Singleton
    fun provideLocalDataSource(productDao: ProductDao): LocalDataSource {
        return LocalDataSourceImpl(productDao)
    }
}