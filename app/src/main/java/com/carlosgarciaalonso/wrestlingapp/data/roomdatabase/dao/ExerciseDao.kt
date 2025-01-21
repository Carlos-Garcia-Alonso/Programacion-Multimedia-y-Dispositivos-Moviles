package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao

import androidx.room.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.Exercise

@Dao
interface ExerciseDao {
    @Insert
    fun insertExercise(exercise: Exercise): Long

    @Query("SELECT * FROM ejercicios")
    fun getAllExercises(): List<Exercise>

    @Query("SELECT * FROM ejercicios WHERE categoria_id = :categoriaId")
    fun getExercisesByCategory(categoriaId: Int): List<Exercise>

    @Update
    fun updateExercise(exercise: Exercise): Int

    @Delete
    fun deleteExercise(exercise: Exercise): Int
}
