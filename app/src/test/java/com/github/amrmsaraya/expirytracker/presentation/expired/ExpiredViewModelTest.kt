package com.github.amrmsaraya.expirytracker.presentation.expired

import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.github.amrmsaraya.expirytracker.domain.usecase.GetExpiredProductsUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ExpiredViewModelTest {
    private lateinit var getExpiredProductsUseCase: GetExpiredProductsUseCase
    private lateinit var expiredViewModel: ExpiredViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        getExpiredProductsUseCase = mockk(relaxed = true)
        expiredViewModel = ExpiredViewModel(
            getExpiredProductsUseCase = getExpiredProductsUseCase,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `sendIntent() with ExpiredIntent_GetProducts then uiState should contain the valid products`() =
        runTest {

            // Given
            val product1 = Product(barcode = "1")
            val product2 = Product(barcode = "2")
            val product3 = Product(barcode = "3")

            every { getExpiredProductsUseCase.invoke() } returns flowOf(
                listOf(
                    product1,
                    product2,
                    product3
                )
            )

            // When
            expiredViewModel.sendIntent(ExpiredIntent.GetProducts)

            // Then
            Truth.assertThat(expiredViewModel.uiState.value.products).containsExactly(
                product1,
                product2,
                product3
            )
        }
}