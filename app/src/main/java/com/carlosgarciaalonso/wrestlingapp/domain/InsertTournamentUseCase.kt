package com.carlosgarciaalonso.wrestlingapp.domain

import com.carlosgarciaalonso.wrestlingapp.data.repository.TorneoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertTournamentUseCase @Inject constructor(private val repository: TorneoRepository) {
    suspend operator fun invoke() {
        withContext(Dispatchers.IO){
            repository.insertInitialDataIfNeeded()
        }
    }
}