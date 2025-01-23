package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao

import androidx.room.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.TournamentWeight
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.Tournament

// Dao con las funciones necesarias para realizar el CRUD de la tabla que relaciona torneos y pesos
@Dao
interface TournamentWeightDao {
    @Insert
    // Cuando una función se marca como "suspend" en el Dao, Room ya se encarga de ejecutarla en
    // segundo plano y no es necesario utilizar "withContext(Dispatchers.IO)"
    suspend fun insertTournamentWeight(tournamentWeight: TournamentWeight): Long

    @Query("SELECT * FROM tournament_weights WHERE tournament_id = :tournamentId")
    suspend fun getWeightsByTournament(tournamentId: Int): List<TournamentWeight>

    @Query("SELECT * FROM tournament_weights WHERE weight_id = :weightId")
    suspend fun getTournamentsByWeight(weightId: Int): List<TournamentWeight>

    @Update
    suspend fun updateTournamentWeight(tournamentWeight: TournamentWeight): Int

    @Delete
    suspend fun deleteTournamentWeight(tournamentWeight: TournamentWeight): Int
}

