package com.carlosgarciaalonso.wrestlingapp.domain

import com.carlosgarciaalonso.wrestlingapp.data.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUsuarioUseCase @Inject constructor(private val repositorio : UsuarioRepository) {
    suspend operator fun invoke() : String {
        return withContext(Dispatchers.IO) {
            repositorio.getUsuario()
        }
    }
}