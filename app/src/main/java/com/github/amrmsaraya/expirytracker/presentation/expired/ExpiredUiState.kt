package com.github.amrmsaraya.expirytracker.presentation.expired

import com.github.amrmsaraya.expirytracker.domain.entity.Product

data class ExpiredUiState(
    val products: List<Product> = emptyList()
)
