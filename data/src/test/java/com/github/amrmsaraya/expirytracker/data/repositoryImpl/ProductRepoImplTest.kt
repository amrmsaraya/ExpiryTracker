package com.github.amrmsaraya.expirytracker.data.repositoryImpl

import com.github.amrmsaraya.expirytracker.data.model.ProductDTO
import com.github.amrmsaraya.expirytracker.data.source.LocalDataSource
import com.github.amrmsaraya.expirytracker.domain.repository.ProductRepo
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductRepoImplTest {

    private lateinit var localDataSource: LocalDataSource
    private lateinit var productRepo: ProductRepo

    private val standardTestDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        localDataSource = mockk(relaxed = true)
        productRepo = ProductRepoImpl(
            localDataSource = localDataSource,
            dispatcher = standardTestDispatcher
        )
    }

    @Test
    fun `insert() with product then insert() in LocalDataSource() should be called`() = runTest {

        // Given
        val product = ProductDTO()

        // When
        localDataSource.insert(product)

        // Then
        coVerify { localDataSource.insert(product) }
    }

    @Test
    fun `delete() with product then delete() in LocalDataSource() should be called`() = runTest {

        // Given
        val product = ProductDTO()

        // When
        localDataSource.delete(product)

        // Then
        coVerify { localDataSource.delete(product) }
    }

    @Test
    fun `getProducts() with product then return the correct list of products`() =
        runTest {

            // Given
            val product1 = ProductDTO(barcode = "12345")
            val product2 = ProductDTO(barcode = "abcdef")
            every { localDataSource.getProducts() } returns flowOf(listOf(product1, product2))

            // When
            val result = localDataSource.getProducts().first()

            // Then
            assertThat(result).containsExactly(product1, product2)
        }

}