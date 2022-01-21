package com.github.amrmsaraya.expirytracker.presentation.home

import com.github.amrmsaraya.expirytracker.domain.entity.Product

data class HomeUiState(
    val products: List<Product> = emptyList()
)
