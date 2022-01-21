package com.github.amrmsaraya.expirytracker.domain.usecase

import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.github.amrmsaraya.expirytracker.domain.repository.ProductRepo

class InsertProductsUseCase(private val productRepo: ProductRepo) {
    suspend fun invoke(product: Product) {
        productRepo.insert(product)
    }
}