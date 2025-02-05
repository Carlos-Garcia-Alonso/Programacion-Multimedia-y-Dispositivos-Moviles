package com.carlosgarciaalonso.wrestlingapp.data.repository

import com.carlosgarciaalonso.wrestlingapp.data.network.ChuckNorrisService
import com.carlosgarciaalonso.wrestlingapp.domain.ChuckNorrisJoke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

public class ChuckRepository @Inject constructor(private val api : ChuckNorrisService) {

    suspend fun getJoke() : ChuckNorrisJoke {
        return withContext(Dispatchers.IO) {
                val response = api.getRandomJoke()
                ChuckNorrisJoke(response.image, response.broma)
        }
    }

}