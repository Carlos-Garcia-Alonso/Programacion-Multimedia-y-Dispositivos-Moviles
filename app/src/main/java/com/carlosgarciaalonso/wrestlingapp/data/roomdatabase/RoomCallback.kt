package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.AppDatabase
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.*

//Esta clase se utiliza para añadir los datos iniciales a la base de datos de Room, estos datos se
// insertarán a través de un "callback" en la clase "WrestlingApplication"
class RoomCallback(private val database: AppDatabase) : RoomDatabase.Callback() {

    // Se utiliza una corutina para hacer los inserts iniciales sin bloquear la interfaz
    // Esta función se ejecutará la primera vez que se cree la base de datos
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            // Inserta datos iniciales
            datosIniciales(database)
        }
    }

    suspend fun datosIniciales(database: AppDatabase) {
        val categoryDao = database.categoryRoomDao()
        val tournamentDao = database.tournamentDao()
        val weightDao = database.weightDao()
        val tournamentCategoryDao = database.tournamentCategoryDao()
        val tournamentWeightDao = database.tournamentWeightDao()

        // Inserta datos iniciales para CategoryRoom
        val categories = listOf(
            CategoryRoom(name = "Lucha Libre Senior Masculina", min_age = 18, max_age = 35),
            CategoryRoom(name = "Lucha Libre Senior Femenina", min_age = 18, max_age = 35),
            CategoryRoom(name = "Lucha Libre Junior Masculina", min_age = 15, max_age = 17),
            CategoryRoom(name = "Lucha Libre Junior Femenina", min_age = 15, max_age = 17)
        )

        //Se guardan los ids de los valores que se introducen para utilizarlos en las claves
        // foráneas de los inserts de las otras tablas
        val categoryIds = mutableListOf<Long>()
        categories.forEach { category ->
            val id = categoryDao.insertCategory(category)
            categoryIds.add(id)
        }

        // Inserta datos iniciales para Tournament
        val tournaments = listOf(
            Tournament(date = "2025-02-15", city = "Santiago", time = "10:00"),
            Tournament(date = "2025-06-04", city = "Barcelona", time = "15:00"),
            Tournament(date = "2025-01-25", city = "Santiago", time = "09:00")
        )

        //Se guardan los ids de los valores que se introducen para utilizarlos en las claves
        // foráneas de los inserts de las otras tablas
        val tournamentIds = mutableListOf<Long>()
        for (tournament in tournaments) {
            val id = tournamentDao.insertTournament(tournament)
            tournamentIds.add(id)
        }

        // Inserta datos iniciales para Weight
        val weights = listOf(
            Weight(weight = 65.0, category_id = categoryIds[0].toInt()),
            Weight(weight = 75.0, category_id = categoryIds[0].toInt()),
            Weight(weight = 50.0, category_id = categoryIds[0].toInt()),
            Weight(weight = 55.0, category_id = categoryIds[0].toInt()),
            Weight(weight = 45.0, category_id = categoryIds[0].toInt()),
            Weight(weight = 65.0, category_id = categoryIds[1].toInt()),
            Weight(weight = 75.0, category_id = categoryIds[1].toInt()),
            Weight(weight = 50.0, category_id = categoryIds[1].toInt()),
            Weight(weight = 55.0, category_id = categoryIds[1].toInt()),
            Weight(weight = 45.0, category_id = categoryIds[1].toInt()),
            Weight(weight = 65.0, category_id = categoryIds[2].toInt()),
            Weight(weight = 75.0, category_id = categoryIds[2].toInt()),
            Weight(weight = 50.0, category_id = categoryIds[2].toInt()),
            Weight(weight = 55.0, category_id = categoryIds[2].toInt()),
            Weight(weight = 45.0, category_id = categoryIds[2].toInt()),
            Weight(weight = 65.0, category_id = categoryIds[3].toInt()),
            Weight(weight = 75.0, category_id = categoryIds[3].toInt()),
            Weight(weight = 50.0, category_id = categoryIds[3].toInt()),
            Weight(weight = 55.0, category_id = categoryIds[3].toInt()),
            Weight(weight = 45.0, category_id = categoryIds[3].toInt()),
        )

        //Se guardan los ids de los valores que se introducen para utilizarlos en las claves
        // foráneas de los inserts de las otras tablas
        val weightIds = mutableListOf<Long>()
        for (weight in weights) {
            val id = weightDao.insertWeight(weight)
            weightIds.add(id)
        }

        // Inserta datos iniciales para TournamentCategory (Se asocian id de torneo e id de categoria)
        val tournamentCategories = listOf(
            TournamentCategory(
                tournament_id = tournamentIds[0].toInt(),
                category_id = categoryIds[0].toInt()
            ),
            TournamentCategory(
                tournament_id = tournamentIds[0].toInt(),
                category_id = categoryIds[1].toInt()
            ),
            TournamentCategory(
                tournament_id = tournamentIds[1].toInt(),
                category_id = categoryIds[0].toInt()
            ),
            TournamentCategory(
                tournament_id = tournamentIds[1].toInt(),
                category_id = categoryIds[1].toInt()
            ),
            TournamentCategory(
                tournament_id = tournamentIds[2].toInt(),
                category_id = categoryIds[2].toInt()
            ),
            TournamentCategory(
                tournament_id = tournamentIds[2].toInt(),
                category_id = categoryIds[3].toInt()
            )
        )

        for (tournamentCategory in tournamentCategories) {
            tournamentCategoryDao.insertTournamentCategory(tournamentCategory)
        }

        // Inserta datos iniciales para TournamentWeight (Se asocian id de torneo e id de peso)
        val tournamentWeights = listOf(
            // Torneo 0 (Lucha Libre Senior Masculina y Femenina)
            TournamentWeight(tournament_id = tournamentIds[0].toInt(), weight_id = weightIds[0].toInt()), // Senior Masculina - Peso 65.0
            TournamentWeight(tournament_id = tournamentIds[0].toInt(), weight_id = weightIds[1].toInt()), // Senior Masculina - Peso 75.0
            TournamentWeight(tournament_id = tournamentIds[0].toInt(), weight_id = weightIds[2].toInt()), // Senior Masculina - Peso 50.0
            TournamentWeight(tournament_id = tournamentIds[0].toInt(), weight_id = weightIds[3].toInt()), // Senior Masculina - Peso 55.0
            TournamentWeight(tournament_id = tournamentIds[0].toInt(), weight_id = weightIds[4].toInt()), // Senior Masculina - Peso 45.0
            TournamentWeight(tournament_id = tournamentIds[0].toInt(), weight_id = weightIds[5].toInt()), // Senior Femenina - Peso 65.0
            TournamentWeight(tournament_id = tournamentIds[0].toInt(), weight_id = weightIds[6].toInt()), // Senior Femenina - Peso 75.0
            TournamentWeight(tournament_id = tournamentIds[0].toInt(), weight_id = weightIds[7].toInt()), // Senior Femenina - Peso 50.0
            TournamentWeight(tournament_id = tournamentIds[0].toInt(), weight_id = weightIds[8].toInt()), // Senior Femenina - Peso 55.0
            TournamentWeight(tournament_id = tournamentIds[0].toInt(), weight_id = weightIds[9].toInt()), // Senior Femenina - Peso 45.0

            // Torneo 1 (Lucha Libre Senior Masculina y Femenina)
            TournamentWeight(tournament_id = tournamentIds[1].toInt(), weight_id = weightIds[0].toInt()), // Senior Masculina - Peso 65.0
            TournamentWeight(tournament_id = tournamentIds[1].toInt(), weight_id = weightIds[1].toInt()), // Senior Masculina - Peso 75.0
            TournamentWeight(tournament_id = tournamentIds[1].toInt(), weight_id = weightIds[2].toInt()), // Senior Masculina - Peso 50.0
            TournamentWeight(tournament_id = tournamentIds[1].toInt(), weight_id = weightIds[3].toInt()), // Senior Masculina - Peso 55.0
            TournamentWeight(tournament_id = tournamentIds[1].toInt(), weight_id = weightIds[4].toInt()), // Senior Masculina - Peso 45.0
            TournamentWeight(tournament_id = tournamentIds[1].toInt(), weight_id = weightIds[5].toInt()), // Senior Femenina - Peso 65.0
            TournamentWeight(tournament_id = tournamentIds[1].toInt(), weight_id = weightIds[6].toInt()), // Senior Femenina - Peso 75.0
            TournamentWeight(tournament_id = tournamentIds[1].toInt(), weight_id = weightIds[7].toInt()), // Senior Femenina - Peso 50.0
            TournamentWeight(tournament_id = tournamentIds[1].toInt(), weight_id = weightIds[8].toInt()), // Senior Femenina - Peso 55.0
            TournamentWeight(tournament_id = tournamentIds[1].toInt(), weight_id = weightIds[9].toInt()), // Senior Femenina - Peso 45.0

            // Torneo 2 (Lucha Libre Junior Masculina y Femenina)
            TournamentWeight(tournament_id = tournamentIds[2].toInt(), weight_id = weightIds[10].toInt()), // Junior Masculina - Peso 65.0
            TournamentWeight(tournament_id = tournamentIds[2].toInt(), weight_id = weightIds[11].toInt()), // Junior Masculina - Peso 75.0
            TournamentWeight(tournament_id = tournamentIds[2].toInt(), weight_id = weightIds[12].toInt()), // Junior Masculina - Peso 50.0
            TournamentWeight(tournament_id = tournamentIds[2].toInt(), weight_id = weightIds[13].toInt()), // Junior Masculina - Peso 55.0
            TournamentWeight(tournament_id = tournamentIds[2].toInt(), weight_id = weightIds[14].toInt()), // Junior Masculina - Peso 45.0
            TournamentWeight(tournament_id = tournamentIds[2].toInt(), weight_id = weightIds[15].toInt()), // Junior Femenina - Peso 65.0
            TournamentWeight(tournament_id = tournamentIds[2].toInt(), weight_id = weightIds[16].toInt()), // Junior Femenina - Peso 75.0
            TournamentWeight(tournament_id = tournamentIds[2].toInt(), weight_id = weightIds[17].toInt()), // Junior Femenina - Peso 50.0
            TournamentWeight(tournament_id = tournamentIds[2].toInt(), weight_id = weightIds[18].toInt()), // Junior Femenina - Peso 55.0
            TournamentWeight(tournament_id = tournamentIds[2].toInt(), weight_id = weightIds[19].toInt())  // Junior Femenina - Peso 45.0
        )

        for (tournamentWeight in tournamentWeights) {
            tournamentWeightDao.insertTournamentWeight(tournamentWeight)
        }

    }
}

