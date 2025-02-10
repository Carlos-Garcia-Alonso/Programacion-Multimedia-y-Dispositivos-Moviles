package com.carlosgarciaalonso.wrestlingapp.domain

import com.carlosgarciaalonso.wrestlingapp.data.repository.TorneoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTournamentUseCase @Inject constructor(private val repositorio : TorneoRepository) {

    operator fun invoke() : Flow<List<TournamentWithCategories>> {
       //No es necesario un context cuando se devuelve un flow porque ya maneja él la asincronía
       return repositorio.getAllTournamentsWithCategoriesFlow()

    }
}