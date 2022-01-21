package com.github.amrmsaraya.expirytracker.data.source

import com.github.amrmsaraya.expirytracker.data.model.ProductDTO
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insert(product: ProductDTO)
    suspend fun delete(product: ProductDTO)
    fun getProducts(): Flow<List<ProductDTO>>
}