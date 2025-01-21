package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryRoom(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String, // Nombre de la categoría (e.g., "Lucha Libre Senior Masculina")
    val min_age: Int, // Edad mínima
    val max_age: Int  // Edad máxima
)