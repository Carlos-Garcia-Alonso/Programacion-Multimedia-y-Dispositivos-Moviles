package com.carlosgarciaalonso.wrestlingapp

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.AppDatabase
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.RoomCallback
import com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.WrestlingSqliteHelper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@HiltAndroidApp
class WrestlingApplication : Application() {

    //lateinit se utiliza para declarar una variable que no puede ser null pero que no vas a
    // inicializar en este momento; es como decirle al programa "fiate que la voy a inicializar
    // antes de usarla"
    // Aquí se declara la variable "dbHelper" como una propiedad de "WrestlingApplication", lo que
    // permite que sea compartida por todos los repositorios, asegurando que se inicializa una sola vez
    // y no se creen múltiples instancias de la base de datos durante la ejecución.
    lateinit var dbHelper: WrestlingSqliteHelper

//    lateinit var roomDatabase: AppDatabase // Lateinit para la base de datos de Room
    // Esta variable define la corutina para que el insert de datos iniciales de la base de datos de
    // Room con el callback se realice en segundo plano.
    private val applicationScope = CoroutineScope(Job() + Dispatchers.IO)

    val TAG = "WrestlingApplication"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "WrestlingApplication onCreate")

        dbHelper = WrestlingSqliteHelper(this) // Crear una única instancia


        // Inicialización única de la base de datos Room
//        roomDatabase = Room.databaseBuilder(
//            this,
//            AppDatabase::class.java,
//            "tournament_database" // Nombre de la base de datos
//        ) .build()

    }

    override fun onTerminate() {
        super.onTerminate()
        dbHelper.close()
        applicationScope.cancel() // Cancela todas las coroutines asociadas
        Log.d(TAG, "La aplicación se ha cerrado por completo")
    }

    //Notificación al rotar la pantalla que se maneja a nivel de aplicación y no a nivel de Activity
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
            || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            Log.d(TAG, "La aplicación ha rotado: orientación actual " +
                    if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        "vertical"
                    } else {
                        "horizontal"
                    }
            )
        }
    }

    // El compilador me sugiere que añada explícitamente "@Deprecated" para indicar explícitamente
    // que es una función funcional que tiene alternativas más modernas
    @Deprecated("Deprecated in Java")
    override fun onLowMemory() {
        super.onLowMemory()
        //Utilizo "Log.w" en lugar de "Log.d" para marcarlo como un Warning
        Log.w(TAG, "Queda poca memoria en el dispositivo (onLowMemory)")
    }


}