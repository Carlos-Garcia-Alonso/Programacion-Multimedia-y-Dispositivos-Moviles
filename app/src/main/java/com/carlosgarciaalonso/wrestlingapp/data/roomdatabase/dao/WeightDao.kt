package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao

import androidx.room.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.Weight

@Dao
interface WeightDao {
    @Insert
    suspend fun insertWeight(weight: Weight): Long

    @Query("SELECT * FROM weights WHERE category_id = :categoryId")
    suspend fun getWeightsByCategory(categoryId: Int): List<Weight>

    @Update
    suspend fun updateWeight(weight: Weight): Int

    @Delete
    suspend fun deleteWeight(weight: Weight): Int
}

