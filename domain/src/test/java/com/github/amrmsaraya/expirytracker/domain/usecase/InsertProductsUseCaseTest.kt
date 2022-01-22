package com.github.amrmsaraya.expirytracker.domain.usecase

import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.github.amrmsaraya.expirytracker.domain.repository.ProductRepo
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class InsertProductsUseCaseTest {

    private lateinit var productRepo: ProductRepo
    private lateinit var insertProductsUseCase: InsertProductsUseCase

    @Before
    fun setUp() {
        productRepo = mockk(relaxed = true)
        insertProductsUseCase = InsertProductsUseCase(productRepo)
    }

    @Test
    fun `invoke() then return list of expired products`() = runTest {

        // Given
        val product = Product()

        // When
        insertProductsUseCase.invoke(product)

        // Then
        coVerify { productRepo.insert(product) }
    }

}