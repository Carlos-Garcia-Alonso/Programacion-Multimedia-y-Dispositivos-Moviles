package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.combinados

data class TournamentWithCategories(
    val id: Int,
    val date: String,
    val city: String,
    val time: String,
    val categories: String // Categorías concatenadas en una sola cadena
)