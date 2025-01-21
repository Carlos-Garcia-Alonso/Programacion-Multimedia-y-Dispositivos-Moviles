package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao

import androidx.room.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.TournamentCategory

@Dao
interface TournamentCategoryDao {
    @Insert
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

