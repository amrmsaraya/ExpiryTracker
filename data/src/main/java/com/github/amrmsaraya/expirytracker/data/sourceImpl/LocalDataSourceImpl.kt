package com.github.amrmsaraya.expirytracker.data.sourceImpl

import com.github.amrmsaraya.expirytracker.data.local.ProductDao
import com.github.amrmsaraya.expirytracker.data.model.ProductDTO
import com.github.amrmsaraya.expirytracker.data.source.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val productDao: ProductDao) : LocalDataSource {
    override suspend fun insert(product: ProductDTO) {
        productDao.insert(product)
    }

    override suspend fun delete(product: ProductDTO) {
        productDao.delete(product)
    }

    override fun getProducts(): Flow<List<ProductDTO>> {
        return productDao.getProducts()
    }
}