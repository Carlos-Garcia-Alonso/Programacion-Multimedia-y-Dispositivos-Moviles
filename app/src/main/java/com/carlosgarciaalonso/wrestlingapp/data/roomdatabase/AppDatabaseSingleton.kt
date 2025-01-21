package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase


import android.content.Context
import androidx.room.Room

object AppDatabaseSingleton {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        // Si ya existe, devuelve la instancia; de lo contrario, la crea
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "room_database.db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}

//Para utilizar esta clase Singleton desde el MainActivity, este sería un ejemplo:

// val database = AppDatabaseSingleton.getInstance(applicationContext)
// val exerciseDao = database.exerciseDao()
// CoroutineScope(Dispatchers.IO).launch {
//            try {
//                // Obtén todos los ejercicios
//                val exercises = exerciseDao.getAllExercises()
//
//                // Muestra los ejercicios en el Logcat
//                exercises.forEach { exercise ->
//                    Log.d("MainActivity", "Ejercicio: ${exercise.nombre}")
//                }
//            } catch (e: Exception) {
//                // Manejo de errores
//                Log.e("MainActivity", "Error al obtener los ejercicios", e)
//            }
//        }