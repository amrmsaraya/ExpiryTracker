package com.github.amrmsaraya.expirytracker.data.repositoryImpl

import com.github.amrmsaraya.expirytracker.data.mapper.toProduct
import com.github.amrmsaraya.expirytracker.data.mapper.toProductDTO
import com.github.amrmsaraya.expirytracker.data.source.LocalDataSource
import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.github.amrmsaraya.expirytracker.domain.repository.ProductRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProductRepoImpl(
    private val localDataSource: LocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepo {
    override suspend fun insert(product: Product) {
        withContext(dispatcher) {
            localDataSource.insert(product.toProductDTO())
        }
    }

    override suspend fun delete(product: Product) {
        withContext(dispatcher) {
            localDataSource.delete(product.toProductDTO())
        }
    }

    override fun getProducts(): Flow<List<Product>> {
        return localDataSource.getProducts().map {
            it.map { productDTO ->
                productDTO.toProduct()
            }
        }
    }
}