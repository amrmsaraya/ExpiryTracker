package com.github.amrmsaraya.expirytracker.presentation.details

data class DetailsUiState(
    val barcode: String = "",
    val name: String = "",
    val category: String = "",
    val expiryDate: Long = 0L,
)
