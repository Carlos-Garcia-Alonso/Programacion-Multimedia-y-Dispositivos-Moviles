package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//Clase que define la tabla de pesos
@Entity(
    tableName = "weights",
    foreignKeys = [
        ForeignKey(
            entity = CategoryRoom::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Weight(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weight: Double, // Peso en kilogramos
    val category_id: Int // Referencia a la categoría
)
