package com.github.amrmsaraya.expirytracker.presentation.home

import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.github.amrmsaraya.expirytracker.domain.usecase.GetValidProductsUseCase
import com.github.amrmsaraya.expirytracker.domain.usecase.InsertProductsUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var getValidProductsUseCase: GetValidProductsUseCase
    private lateinit var insertProductsUseCase: InsertProductsUseCase
    private lateinit var homeViewModel: HomeViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        getValidProductsUseCase = mockk(relaxed = true)
        insertProductsUseCase = mockk(relaxed = true)
        homeViewModel = HomeViewModel(
            getValidProductsUseCase = getValidProductsUseCase,
            insertProductsUseCase = insertProductsUseCase,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `sendIntent() with HomeIntent_GetProducts then uiState should contain the valid products`() =
        runTest {

            // Given
            val product1 = Product(barcode = "1")
            val product2 = Product(barcode = "2")
            val product3 = Product(barcode = "3")

            every { getValidProductsUseCase.invoke() } returns flowOf(
                listOf(
                    product1,
                    product2,
                    product3
                )
            )

            // When
            homeViewModel.sendIntent(HomeIntent.GetProducts)

            // Then
            assertThat(homeViewModel.uiState.value.products).containsExactly(
                product1,
                product2,
                product3
            )
        }

    @Test
    fun `sendIntent() with HomeIntent_InsertProduct() then uiState should contain the valid products`() =
        runTest {

            // Given
            val product = Product()

            // When
            homeViewModel.sendIntent(HomeIntent.InsertProduct(product))

            // Then
            coVerify { insertProductsUseCase.invoke(product) }
        }
}