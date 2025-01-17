package com.carlosgarciaalonso.wrestlingapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WrestlingSqliteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crear todas las tablas definidas en el contrato
        db.execSQL(WrestlingSqliteContract.UserEntry.SQL_CREATE_ENTRIES)
        db.execSQL(WrestlingSqliteContract.CategoryEntry.SQL_CREATE_ENTRIES)
        db.execSQL(WrestlingSqliteContract.ExerciseEntry.SQL_CREATE_ENTRIES)
        db.execSQL(WrestlingSqliteContract.ProgressEntry.SQL_CREATE_ENTRIES)

        // Insertar datos iniciales en la tabla de categorías
        db.execSQL("INSERT INTO ${WrestlingSqliteContract.CategoryEntry.TABLE_NAME} " +
                "(${WrestlingSqliteContract.CategoryEntry.COLUMN_NAME_NOMBRE}) VALUES " +
                "('Strength'), ('Cardio'), ('Flexibility')")

        // Insertar datos iniciales en la tabla de ejercicios
        db.execSQL("INSERT INTO ${WrestlingSqliteContract.ExerciseEntry.TABLE_NAME} " +
                "(${WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_NOMBRE}, " +
                "${WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_DESCRIPCION}, " +
                "${WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_CATEGORIA_ID}, " +
                "${WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_DIFICULTAD}) VALUES " +
                "('Push-ups', 'Classic chest exercise', 1, 'facil'), " +
                "('Squats', 'Leg and glute exercise', 1, 'moderado'), " +
                "('Running', 'Cardio exercise for endurance', 2, 'dificil')")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(WrestlingSqliteContract.UserEntry.SQL_DELETE_ENTRIES)
        db.execSQL(WrestlingSqliteContract.CategoryEntry.SQL_DELETE_ENTRIES)
        db.execSQL(WrestlingSqliteContract.ExerciseEntry.SQL_DELETE_ENTRIES)
        db.execSQL(WrestlingSqliteContract.ProgressEntry.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        // Nombre de la base de datos
        const val DATABASE_NAME = "WrestlingSqlite.db"

        // Versión de la base de datos
        const val DATABASE_VERSION = 1
    }
}