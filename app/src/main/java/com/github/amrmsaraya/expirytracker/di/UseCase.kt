package com.github.amrmsaraya.expirytracker.di

import com.github.amrmsaraya.expirytracker.domain.repository.ProductRepo
import com.github.amrmsaraya.expirytracker.domain.usecase.GetExpiredProductsUseCase
import com.github.amrmsaraya.expirytracker.domain.usecase.GetNotNotifiedExpiredProductsUseCase
import com.github.amrmsaraya.expirytracker.domain.usecase.GetValidProductsUseCase
import com.github.amrmsaraya.expirytracker.domain.usecase.InsertProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCase {

    @Singleton
    @Provides
    fun provideGetValidProductsUseCase(productRepo: ProductRepo): GetValidProductsUseCase {
        return GetValidProductsUseCase(productRepo)
    }

    @Singleton
    @Provides
    fun provideGetExpiredProductsUseCase(productRepo: ProductRepo): GetExpiredProductsUseCase {
        return GetExpiredProductsUseCase(productRepo)
    }

    @Singleton
    @Provides
    fun provideGetNotNotifiedExpiredProductsUseCase(productRepo: ProductRepo): GetNotNotifiedExpiredProductsUseCase {
        return GetNotNotifiedExpiredProductsUseCase(productRepo)
    }

    @Singleton
    @Provides
    fun provideInsertProductsUseCase(productRepo: ProductRepo): InsertProductsUseCase {
        return InsertProductsUseCase(productRepo)
    }
}