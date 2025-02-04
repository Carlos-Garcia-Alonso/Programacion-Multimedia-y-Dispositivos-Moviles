package com.carlosgarciaalonso.wrestlingapp.di

import android.content.Context
import androidx.room.Room
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.AppDatabase
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao.CategoryRoomDao
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao.TournamentCategoryDao
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao.TournamentDao
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao.TournamentWeightDao
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao.WeightDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Módulo de Hilt para proveer la instancia de RoomDatabase (AppDatabase) y los DAOs que se van a inyectar.
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        // Construye la base de datos de Room
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "tournament_database" // Nombre de la BD
        ).build()
    }

    // (opcional, solo si se planea inyectar DAOs directamente).
    @Provides
    fun provideTournamentDao(db: AppDatabase): TournamentDao = db.tournamentDao()

    @Provides
    fun provideCategoryRoomDao(db: AppDatabase): CategoryRoomDao = db.categoryRoomDao()

    @Provides
    fun provideWeightDao(db: AppDatabase): WeightDao = db.weightDao()

    @Provides
    fun provideTournamentCategoryDao(db: AppDatabase): TournamentCategoryDao = db.tournamentCategoryDao()

    @Provides
    fun provideTournamentWeightDao(db: AppDatabase): TournamentWeightDao = db.tournamentWeightDao()
}
