package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//Clase que define la tabla que relaciona torneos y categorias
@Entity(
    tableName = "tournament_categories",
    foreignKeys = [
        ForeignKey(
            entity = Tournament::class,
            parentColumns = ["id"],
            childColumns = ["tournament_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryRoom::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TournamentCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tournament_id: Int, // Referencia al torneo
    val category_id: Int // Referencia a la categoría
)

