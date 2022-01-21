package com.github.amrmsaraya.expirytracker.domain.repository

import com.github.amrmsaraya.expirytracker.domain.entity.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepo {
    suspend fun insert(product: Product)
    suspend fun delete(product: Product)
    fun getProducts(): Flow<List<Product>>
}