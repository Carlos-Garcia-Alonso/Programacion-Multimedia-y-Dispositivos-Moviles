package com.carlosgarciaalonso.wrestlingapp.data.repository

import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.AppDatabase
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.RoomCallback
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.combinados.TournamentWithCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
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
     * Devuelve un Flow con la lista de torneos + categorías.
     */
    fun getAllTournamentsWithCategoriesFlow(): Flow<List<TournamentWithCategories>> {
        return db.tournamentDao().getTournamentsWithCategoryFlow()
    }

    /**
     * Ejemplo de insertar datos si la BD está vacía.
     */
    suspend fun insertInitialDataIfNeeded() {
        // Aquí haces la lógica de insert si procede
        val data = db.tournamentDao().getTournamentsWithCategoryFlow().firstOrNull()
        // .firstOrNull() suspende hasta que Flow emita el primer valor,
        // y obtienes la lista inicial. Si está vacío, insertas:
        if (data.isNullOrEmpty()) {
            RoomCallback(db).datosIniciales(db)
        }
    }
}
