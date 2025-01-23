package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao

import androidx.room.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.CategoryRoom

// Dao con las funciones necesarias para realizar el CRUD de la tabla de categorías
@Dao
interface CategoryRoomDao {
    @Insert
    // Cuando una función se marca como "suspend" en el Dao, Room ya se encarga de ejecutarla en
    // segundo plano y no es necesario utilizar "withContext(Dispatchers.IO)"
    suspend fun insertCategory(category: CategoryRoom): Long

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryRoom>

    @Update
    suspend fun updateCategory(category: CategoryRoom): Int

    @Delete
    suspend fun deleteCategory(category: CategoryRoom): Int
}