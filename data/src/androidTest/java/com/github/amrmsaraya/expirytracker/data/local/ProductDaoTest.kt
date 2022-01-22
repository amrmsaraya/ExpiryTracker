package com.github.amrmsaraya.expirytracker.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.amrmsaraya.expirytracker.data.model.ProductDTO
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var productDao: ProductDao

    @Before
    fun initDB() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        productDao = database.productDao()
    }

    @After
    fun closeDB() = database.close()


    @Test
    fun insertProduct_thenProductShouldBeInserted() = runTest {

        // Given
        val product = ProductDTO(barcode = "123456")

        // When
        productDao.insert(product)
        val result = productDao.getProducts().first()

        // Then
        assertThat(result).contains(product)
    }

    @Test
    fun insertProduct_thatAlreadyExists_WithAnotherName_thenItShouldBeReplaced() = runTest {

        // Given
        val product1 = ProductDTO(barcode = "123456", name = "Milk")
        val product2 = ProductDTO(barcode = "123456", name = "Biscuits")
        productDao.insert(product1)

        // When
        productDao.insert(product2)
        val result = productDao.getProducts().first()

        // Then
        assertThat(result).contains(product2)
        assertThat(result).doesNotContain(product1)
    }


    @Test
    fun deleteProduct_thenItShouldBeDeleted() = runTest {

        // Given
        val product = ProductDTO(barcode = "123456")
        productDao.insert(product)

        // When
        productDao.delete(product)
        val result = productDao.getProducts().first()

        // Then
        assertThat(result).doesNotContain(product)
    }

    @Test
    fun getProducts_thenWeShouldHaveAListOfExistingProducts() = runTest {

        // Given
        val product1 = ProductDTO(barcode = "123456")
        val product2 = ProductDTO(barcode = "abcdef")
        productDao.insert(product1)
        productDao.insert(product2)

        // When
        val result = productDao.getProducts().first()

        // Then
        assertThat(result).containsExactly(product1, product2)
    }

    @Test
    fun getProducts_withEmptyTable_thenWeShouldHaveAnEmptyList() = runTest {

        // When
        val result = productDao.getProducts().first()

        // Then
        assertThat(result).isEmpty()
    }
}