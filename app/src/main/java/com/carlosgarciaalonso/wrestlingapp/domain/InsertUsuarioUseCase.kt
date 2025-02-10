package com.carlosgarciaalonso.wrestlingapp.domain

import com.carlosgarciaalonso.wrestlingapp.data.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertUsuarioUseCase @Inject constructor(private val repositorio : UsuarioRepository) {

    suspend operator fun invoke(usuario : String){

        withContext(Dispatchers.IO){
            repositorio.insertUsuario(usuario)
        }

    }

}