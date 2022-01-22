package com.github.amrmsaraya.expirytracker.data.sourceImpl

import com.github.amrmsaraya.expirytracker.data.local.ProductDao
import com.github.amrmsaraya.expirytracker.data.model.ProductDTO
import com.github.amrmsaraya.expirytracker.data.source.LocalDataSource
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LocalDataSourceImplTest {

    private lateinit var productDao: ProductDao
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        productDao = mockk(relaxed = true)
        localDataSource = LocalDataSourceImpl(productDao)
    }

    @Test
    fun `insert() with product then insert() in ProductDao() should be called`() = runTest {

        // Given
        val product = ProductDTO()

        // When
        localDataSource.insert(product)

        // Then
        coVerify { productDao.insert(product) }
    }

    @Test
    fun `delete() with product then delete() in ProductDao() should be called`() = runTest {

        // Given
        val product = ProductDTO()

        // When
        localDataSource.delete(product)

        // Then
        coVerify { productDao.delete(product) }
    }

    @Test
    fun `getProducts() with product then getProducts() in ProductDao() should be called`() =
        runTest {

            // Given
            val product1 = ProductDTO(barcode = "12345")
            val product2 = ProductDTO(barcode = "abcdef")
            every { productDao.getProducts() } returns flowOf(listOf(product1, product2))

            // When
            val result = localDataSource.getProducts().first()

            // Then
            assertThat(result).containsExactly(product1, product2)
        }

}