package com.github.amrmsaraya.expirytracker.data.local

import androidx.room.*
import com.github.amrmsaraya.expirytracker.data.model.ProductDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductDTO)

    @Delete
    suspend fun delete(product: ProductDTO)

    @Query("SELECT * FROM product")
    fun getProducts(): Flow<List<ProductDTO>>
}