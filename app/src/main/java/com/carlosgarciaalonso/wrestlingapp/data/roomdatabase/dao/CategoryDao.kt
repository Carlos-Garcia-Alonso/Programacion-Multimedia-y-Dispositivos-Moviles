package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao

import androidx.room.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.Category

@Dao
interface CategoryDao {
    @Insert
    fun insertCategory(category: Category): Long

    @Query("SELECT * FROM categorias")
    fun getAllCategories(): List<Category>

    @Update
    fun updateCategory(category: Category): Int

    @Delete
    fun deleteCategory(category: Category): Int
}
