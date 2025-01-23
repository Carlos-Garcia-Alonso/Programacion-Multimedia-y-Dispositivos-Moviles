package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//Clase que define la tabla que relaciona torneos y pesos
@Entity(
    tableName = "tournament_weights",
    foreignKeys = [
        ForeignKey(
            entity = Tournament::class,
            parentColumns = ["id"],
            childColumns = ["tournament_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Weight::class,
            parentColumns = ["id"],
            childColumns = ["weight_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TournamentWeight(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tournament_id: Int, // Referencia al torneo
    val weight_id: Int // Referencia al peso específico
)
