package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tournaments")
data class Tournament(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val city: String,
    val time: String
)
