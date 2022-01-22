package com.github.amrmsaraya.expirytracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductDTO(
    @PrimaryKey
    val barcode: String = "",
    val name: String = "",
    val category: String = "",
    val expiryDate: Long = 0L,
    val isNotified: Boolean = false
)