package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao

import androidx.room.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.Tournament

@Dao
interface TournamentDao {
    @Insert
    suspend fun insertTournament(tournament: Tournament): Long

    @Query("SELECT * FROM tournaments")
    suspend fun getAllTournaments(): List<Tournament>

    @Update
    suspend fun updateTournament(tournament: Tournament): Int

    @Delete
    suspend fun deleteTournament(tournament: Tournament): Int
}

