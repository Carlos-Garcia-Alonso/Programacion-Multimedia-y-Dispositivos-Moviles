package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao

import androidx.room.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.TournamentCategory

// Dao con las funciones necesarias para realizar el CRUD de la tabla que relaciona categorias y
// torneos
@Dao
interface TournamentCategoryDao {
    @Insert
    // Cuando una función se marca como "suspend" en el Dao, Room ya se encarga de ejecutarla en
    // segundo plano y no es necesario utilizar "withContext(Dispatchers.IO)"
    suspend fun insertTournamentCategory(tournamentCategory: TournamentCategory): Long

    @Query("SELECT * FROM tournament_categories WHERE tournament_id = :tournamentId")
    suspend fun getCategoriesByTournament(tournamentId: Int): List<TournamentCategory>

    @Query("SELECT * FROM tournament_categories WHERE category_id = :categoryId")
    suspend fun getTournamentsByCategory(categoryId: Int): List<TournamentCategory>

    @Update
    suspend fun updateTournamentCategory(tournamentCategory: TournamentCategory): Int

    @Delete
    suspend fun deleteTournamentCategory(tournamentCategory: TournamentCategory): Int
}

