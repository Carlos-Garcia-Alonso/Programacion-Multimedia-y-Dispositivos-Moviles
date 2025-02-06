package com.carlosgarciaalonso.wrestlingapp.domain

import com.carlosgarciaalonso.wrestlingapp.data.repository.ChuckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetChuckNorrisUseCase @Inject constructor(private val repository: ChuckRepository) {

    suspend operator fun invoke() : ChuckNorrisJoke {

        return withContext(Dispatchers.IO) {
            repository.getJoke()
        }

    }

}