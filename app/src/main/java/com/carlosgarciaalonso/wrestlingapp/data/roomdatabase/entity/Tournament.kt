package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity

import android.text.Html.ImageGetter
import androidx.room.Entity
import androidx.room.PrimaryKey

//Clase que define la tabla de torneos
@Entity(tableName = "tournaments")
data class Tournament(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val city: String,
    val time: String
)
