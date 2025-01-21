package com.carlosgarciaalonso.wrestlingapp.data.roomdatabase


import androidx.room.Database
import androidx.room.RoomDatabase
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.dao.*
import com.carlosgarciaalonso.wrestlingapp.data.roomdatabase.entity.*

@Database(
    entities = [User::class, Category::class, Exercise::class, Progress::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun progressDao(): ProgressDao
}
