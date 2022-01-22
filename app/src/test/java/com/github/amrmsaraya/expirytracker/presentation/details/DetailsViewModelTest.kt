package com.github.amrmsaraya.expirytracker.presentation.details

import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.github.amrmsaraya.expirytracker.domain.usecase.InsertProductsUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailsViewModelTest {


    private lateinit var insertProductsUseCase: InsertProductsUseCase
    private lateinit var detailsViewModel: DetailsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        insertProductsUseCase = mockk(relaxed = true)
        detailsViewModel = DetailsViewModel(
            insertProductsUseCase = insertProductsUseCase,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `sendIntent() with DetailsIntent_InsertProduct() then insertProductsUseCase Function should be called`() =
        runTest {

            // Given
            val product = Product()

            // When
            detailsViewModel.sendIntent(
                DetailsIntent.InsertProduct(product = product, onFinish = {})
            )

            // Then
            coVerify { insertProductsUseCase.invoke(product) }
        }

    @Test
    fun `sendIntent() with DetailsIntent_UpdateBarcode then uiState should contain the valid barcode`() =
        runTest {

            // Given
            val barcode = "123456"

            // When
            detailsViewModel.sendIntent(DetailsIntent.UpdateBarcode(barcode))

            // Then
            assertThat(detailsViewModel.uiState.value.barcode).isEqualTo(barcode)
        }

    @Test
    fun `sendIntent() with DetailsIntent_UpdateName then uiState should contain the valid name`() =
        runTest {

            // Given
            val name = "Milk"

            // When
            detailsViewModel.sendIntent(DetailsIntent.UpdateName(name))

            // Then
            assertThat(detailsViewModel.uiState.value.name).isEqualTo(name)
        }

    @Test
    fun `sendIntent() with DetailsIntent_UpdateCategory then uiState should contain the valid category`() =
        runTest {

            // Given
            val category = "Drinks"

            // When
            detailsViewModel.sendIntent(DetailsIntent.UpdateCategory(category))

            // Then
            assertThat(detailsViewModel.uiState.value.category).isEqualTo(category)
        }

    @Test
    fun `sendIntent() with DetailsIntent_UpdateExpiryDate then uiState should contain the valid ExpiryDate`() =
        runTest {

            // Given
            val expiryDate = 1000L

            // When
            detailsViewModel.sendIntent(DetailsIntent.UpdateExpiryDate(expiryDate))

            // Then
            assertThat(detailsViewModel.uiState.value.expiryDate).isEqualTo(expiryDate)
        }


}