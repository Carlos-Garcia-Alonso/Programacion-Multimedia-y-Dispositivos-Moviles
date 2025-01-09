package com.carlosgarciaalonso.wrestlingapp.data


import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class Wrestlers(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "weight") val weight: Float,
)

@Dao
interface WrestlersDAO {
    @Query("SELECT * FROM Wrestlers")
    fun getAll(): List<Wrestlers>

    @Insert
    fun insertAll(vararg users: Wrestlers)

    @Delete
    fun delete(user: Wrestlers)
}

@Database(version = 1, entities = [Wrestlers::class])
abstract class WrestlingDBRoom : RoomDatabase() {
    abstract fun userDao(): WrestlersDAO
}