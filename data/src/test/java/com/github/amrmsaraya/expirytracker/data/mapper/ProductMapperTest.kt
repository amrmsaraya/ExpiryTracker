package com.github.amrmsaraya.expirytracker.data.mapper

import com.github.amrmsaraya.expirytracker.data.model.ProductDTO
import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ProductMapperTest {

    @Test
    fun `toProduct() with ProductDTO then should return a Product`() {

        // Given
        val productDTO = ProductDTO(
            barcode = "123456",
            name = "Milk",
            category = "Drinks",
            expiryDate = 1,
            isNotified = false
        )

        // When
        val result = productDTO.toProduct()

        // Then
        assertThat(result).isEqualTo(
            Product(
                barcode = "123456",
                name = "Milk",
                category = "Drinks",
                expiryDate = 1,
                isNotified = false
            )
        )
    }

    @Test
    fun `toProductDTO() with Product then should return a ProductDTO`() {

        // Given
        val product = Product(
            barcode = "123456",
            name = "Milk",
            category = "Drinks",
            expiryDate = 1,
            isNotified = false
        )

        // When
        val result = product.toProductDTO()

        // Then
        assertThat(result).isEqualTo(
            ProductDTO(
                barcode = "123456",
                name = "Milk",
                category = "Drinks",
                expiryDate = 1,
                isNotified = false
            )
        )
    }
}