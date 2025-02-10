package com.carlosgarciaalonso.wrestlingapp.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsuarioModule {

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context : Context) : SharedPreferences {
        return context.getSharedPreferences("usuarios", Context.MODE_PRIVATE)
    }
}