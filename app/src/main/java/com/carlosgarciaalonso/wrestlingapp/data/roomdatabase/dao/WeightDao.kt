package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao

import androidx.room.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.Weight

// Dao con las funciones necesarias para realizar el CRUD de la tabla de pesos
@Dao
interface WeightDao {
    @Insert
    // Cuando una función se marca como "suspend" en el Dao, Room ya se encarga de ejecutarla en
    // segundo plano y no es necesario utilizar "withContext(Dispatchers.IO)"
    suspend fun insertWeight(weight: Weight): Long

    @Query("SELECT * FROM weights WHERE category_id = :categoryId")
    suspend fun getWeightsByCategory(categoryId: Int): List<Weight>

    @Update
    suspend fun updateWeight(weight: Weight): Int

    @Delete
    suspend fun deleteWeight(weight: Weight): Int
}

