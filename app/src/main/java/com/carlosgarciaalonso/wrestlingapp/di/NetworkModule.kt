package com.carlosgarciaalonso.wrestlingapp.di

import com.carlosgarciaalonso.wrestlingapp.data.network.ChuckNorrisService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

//Módulo de Hilt para proveer la instancia de Retrofit y de ChuckNorrisService.
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        // Creamos la instancia de Retrofit
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideChuckNorrisService(retrofit: Retrofit): ChuckNorrisService {
        // Creamos la instancia del servicio ChuckNorrisService
        return retrofit.create(ChuckNorrisService::class.java)
    }
}