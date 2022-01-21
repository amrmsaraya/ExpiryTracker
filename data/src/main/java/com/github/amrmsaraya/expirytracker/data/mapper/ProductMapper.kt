package com.github.amrmsaraya.expirytracker.data.mapper

import com.github.amrmsaraya.expirytracker.data.model.ProductDTO
import com.github.amrmsaraya.expirytracker.domain.entity.Product

fun ProductDTO.toProduct(): Product {
    return Product(
        barcode = barcode,
        name = name,
        category = category,
        expiryDate = expiryDate,
        isNotified = isNotified
    )
}

fun Product.toProductDTO(): ProductDTO {
    return ProductDTO(
        barcode = barcode,
        name = name,
        category = category,
        expiryDate = expiryDate,
        isNotified = isNotified
    )
}