package com.carlosgarciaalonso.wrestlingapp.data.repository

import android.content.SharedPreferences
import com.carlosgarciaalonso.wrestlingapp.di.UsuarioModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepository @Inject constructor(private val preferences: SharedPreferences) {

    suspend fun getUsuario() : String? {
        return preferences.getString("usuario", "[usuario desconocido]")
    }

    suspend fun insertUsuario(usuario : String) {
        preferences.edit().putString("usuario", usuario).apply()
    }

}