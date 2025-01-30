package com.carlosgarciaalonso.wrestlingapp.data.repository

import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.AppDatabase
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.RoomCallback
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.combinados.TournamentWithCategories
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio para manejar la lógica de acceso a la tabla de torneos
 * (y sus categorías, pesos, etc.) en Room.
 */
@Singleton
class TorneoRepository @Inject constructor(
    private val db: AppDatabase
) {

    /**
     * Devuelve la lista de torneos, unida con las categorías correspondientes.
     */
    suspend fun getAllTournamentsWithCategories(): List<TournamentWithCategories> {
        return db.tournamentDao().getTournamentsWithCategory()
    }

    /**
     * Comprueba si hay datos en la tabla de torneos; si no, inserta algunos.
     */
    suspend fun insertInitialDataIfNeeded() {
        val data = db.tournamentDao().getTournamentsWithCategory()
        if (data.isEmpty()) {
            // Inserta datos iniciales
            RoomCallback(db).datosIniciales(db)
        }
    }
}
