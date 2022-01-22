package com.github.amrmsaraya.expirytracker.presentation.details

import com.github.amrmsaraya.expirytracker.domain.entity.Product

sealed class DetailsIntent {
    data class UpdateBarcode(val barcode: String) : DetailsIntent()
    data class UpdateName(val name: String) : DetailsIntent()
    data class UpdateCategory(val category: String) : DetailsIntent()
    data class UpdateDate(val date: Long) : DetailsIntent()
    data class InsertProduct(val product: Product, val onFinish: () -> Unit) : DetailsIntent()
}