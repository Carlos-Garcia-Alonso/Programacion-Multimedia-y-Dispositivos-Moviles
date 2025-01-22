package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao

import androidx.room.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.combinados.TournamentWithCategories
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.Tournament

@Dao
interface TournamentDao {
    @Insert
    suspend fun insertTournament(tournament: Tournament): Long

    @Query("SELECT * FROM tournaments ORDER BY date ASC")
    suspend fun getAllTournaments(): List<Tournament>

    @Query("""
    SELECT t.id, t.date, t.city, t.time, GROUP_CONCAT(c.name, ', ') AS categories 
    FROM tournaments AS t
    INNER JOIN tournament_categories AS tc ON t.id = tc.tournament_id
    INNER JOIN categories AS c ON tc.category_id = c.id
    GROUP BY t.id
    ORDER BY t.date ASC
""")
    suspend fun getTournamentsWithCategory(): List<TournamentWithCategories>

    @Update
    suspend fun updateTournament(tournament: Tournament): Int

    @Delete
    suspend fun deleteTournament(tournament: Tournament): Int
}

