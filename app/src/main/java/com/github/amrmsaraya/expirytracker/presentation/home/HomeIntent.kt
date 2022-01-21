package com.github.amrmsaraya.expirytracker.presentation.home

import com.github.amrmsaraya.expirytracker.domain.entity.Product

sealed class HomeIntent {
    object GetProducts : HomeIntent()
    data class InsertProduct(val product: Product) : HomeIntent()
}