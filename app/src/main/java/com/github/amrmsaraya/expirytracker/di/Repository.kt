package com.github.amrmsaraya.expirytracker.di

import com.github.amrmsaraya.expirytracker.data.repositoryImpl.ProductRepoImpl
import com.github.amrmsaraya.expirytracker.data.source.LocalDataSource
import com.github.amrmsaraya.expirytracker.domain.repository.ProductRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Repository {

    @Provides
    @Singleton
    fun provideForecastRepo(localDataSource: LocalDataSource): ProductRepo {
        return ProductRepoImpl(localDataSource)
    }
}