package com.carlosgarciaalonso.wrestlingapp

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.util.Log
import com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.WrestlingSqliteHelper

class WrestlingApplication : Application() {

    //lateinit se utiliza para declarar una variable que no puede ser null pero que no vas a
    // inicializar en este momento; es como decirle al programa "fiate que la voy a inicializar
    // antes de usarla"
    // Aquí se declara la variable "dbHelper" como una propiedad de "WrestlingApplication", lo que
    // permite que sea compartida por todos los repositorios, asegurando que se inicializa una sola vez
    // y no se creen múltiples instancias de la base de datos durante la ejecución.
    lateinit var dbHelper: WrestlingSqliteHelper

    val TAG = "WrestlingApplication"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "WrestlingApplication onCreate")

        dbHelper = WrestlingSqliteHelper(this) // Crear una única instancia
    }

    override fun onTerminate() {
        super.onTerminate()
        dbHelper.close()
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