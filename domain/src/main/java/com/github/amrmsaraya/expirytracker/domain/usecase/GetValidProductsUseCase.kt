package com.github.amrmsaraya.expirytracker.domain.usecase

import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.github.amrmsaraya.expirytracker.domain.repository.ProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetValidProductsUseCase(private val productRepo: ProductRepo) {
    fun invoke(): Flow<List<Product>> {
        return productRepo.getProducts().map {
            it.filter { product ->
                product.expireDate > System.currentTimeMillis()
            }.sortedBy { product ->
                product.expireDate
            }
        }
    }
}