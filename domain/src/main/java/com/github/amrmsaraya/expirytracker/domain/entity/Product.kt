package com.github.amrmsaraya.expirytracker.domain.entity

data class Product(
    val barcode: String,
    val name: String,
    val category: String,
    val expireDate: Long,
    val isNotified: Boolean = false
)