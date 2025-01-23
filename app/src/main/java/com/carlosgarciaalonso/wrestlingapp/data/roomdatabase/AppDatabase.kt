package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.*

// Clase que defione la configuración de la base de datos, estableciendo la version y vinculando los
// Entity y los Dao que hemos definido
@Database(
    entities = [Tournament::class, CategoryRoom::class, Weight::class, TournamentCategory::class,
        TournamentWeight::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tournamentDao(): TournamentDao
    abstract fun categoryRoomDao() : CategoryRoomDao
    abstract fun weightDao(): WeightDao
    abstract fun tournamentCategoryDao(): TournamentCategoryDao
    abstract fun tournamentWeightDao(): TournamentWeightDao

}

