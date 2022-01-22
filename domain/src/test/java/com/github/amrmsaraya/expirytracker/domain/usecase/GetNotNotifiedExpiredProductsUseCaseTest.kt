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
class GetNotNotifiedExpiredProductsUseCaseTest {

    private lateinit var productRepo: ProductRepo
    private lateinit var getNotNotifiedExpiredProductsUseCase: GetNotNotifiedExpiredProductsUseCase
    private var currentTime: Long = 0

    @Before
    fun setUp() {
        productRepo = mockk(relaxed = true)
        getNotNotifiedExpiredProductsUseCase = GetNotNotifiedExpiredProductsUseCase(productRepo)
        currentTime = System.currentTimeMillis()
    }

    @Test
    fun `invoke() then return list of expired products which hasn't been notified yet`() = runTest {

        // Given
        val product1 =
            Product(barcode = "1", expiryDate = currentTime - 60 * 1000, isNotified = true)
        val product2 =
            Product(barcode = "2", expiryDate = currentTime - 60 * 1000, isNotified = true)
        val product3 = Product(barcode = "3", expiryDate = currentTime - 60 * 1000)
        val product4 = Product(barcode = "4", expiryDate = currentTime - 60 * 1000)
        val product5 = Product(barcode = "5", expiryDate = currentTime + 60 * 1000)
        val product6 = Product(barcode = "6", expiryDate = currentTime + 60 * 1000)

        coEvery { productRepo.getProducts() } returns flowOf(
            listOf(
                product1,
                product2,
                product3,
                product4,
                product5,
                product6,
            )
        )

        // When
        val result = getNotNotifiedExpiredProductsUseCase.invoke().first()

        // Then
        assertThat(result).containsExactly(product3, product4)
    }

}