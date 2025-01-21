package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao

import androidx.room.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.Progress

@Dao
interface ProgressDao {
    @Insert
    fun insertProgress(progress: Progress): Long

    @Query("SELECT * FROM progreso WHERE user_id = :userId")
    fun getProgressByUser(userId: Int): List<Progress>

    @Update
    fun updateProgress(progress: Progress): Int

    @Delete
    fun deleteProgress(progress: Progress): Int
}
