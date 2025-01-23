package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.combinados

// Clase para almacenar la información de los torneos con la información de las categorías que
// participan en ellos
data class TournamentWithCategories(
    val id: Int,
    val date: String,
    val city: String,
    val time: String,
    val categories: String // Categorías concatenadas en una sola cadena
)