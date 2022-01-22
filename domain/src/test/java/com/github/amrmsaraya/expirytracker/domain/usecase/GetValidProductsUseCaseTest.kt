package com.github.amrmsaraya.expirytracker.domain.usecase

import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.github.amrmsaraya.expirytracker.domain.repository.ProductRepo
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetValidProductsUseCaseTest {

    private lateinit var productRepo: ProductRepo
    private lateinit var getValidProductsUseCase: GetValidProductsUseCase
    private var currentTime: Long = 0

    @Before
    fun setUp() {
        productRepo = mockk(relaxed = true)
        getValidProductsUseCase = GetValidProductsUseCase(productRepo)
        currentTime = System.currentTimeMillis()
    }

    @Test
    fun `invoke() then return list of expired products`() = runTest {

        // Given
        val product1 = Product(barcode = "1", expiryDate = currentTime - 60 * 1000)
        val product2 = Product(barcode = "2", expiryDate = currentTime - 60 * 1000)
        val product3 = Product(barcode = "3", expiryDate = currentTime + 60 * 1000)
        val product4 = Product(barcode = "4", expiryDate = currentTime + 60 * 1000)

        coEvery { productRepo.getProducts() } returns flowOf(
            listOf(
                product1,
                product2,
                product3,
                product4
            )
        )

        // When
        val result = getValidProductsUseCase.invoke().first()

        // Then
        assertThat(result).containsExactly(product3, product4)
    }

}