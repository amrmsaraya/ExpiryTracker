package com.github.amrmsaraya.expirytracker.di

import android.content.Context
import com.github.amrmsaraya.expirytracker.data.local.AppDatabase
import com.github.amrmsaraya.expirytracker.data.local.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Room {

    @Singleton
    @Provides
    fun provideProductDao(@ApplicationContext context: Context): ProductDao {
        return AppDatabase.getDatabase(context).productDao()
    }
}