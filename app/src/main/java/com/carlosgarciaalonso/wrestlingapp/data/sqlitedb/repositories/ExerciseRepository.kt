package com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.carlosgarciaalonso.wrestlingapp.WrestlingApplication
import com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.WrestlingSqliteContract
import com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.WrestlingSqliteHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseRepository(context: Context) {
    // Se crea una variable "dbHelper" que reutiliza la instancia definida en la clase
    // "WrestlingApplication". Esto asegura que, independientemente del repositorio que acceda a la base
    // de datos, todos utilicen la misma instancia única durante la ejecución de la aplicación.
    private val dbHelper = (context.applicationContext as WrestlingApplication).dbHelper

    // Create
    fun insertExercise(nombre: String, descripcion: String, categoriaId: Long, dificultad: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_NOMBRE, nombre)
            put(WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_DESCRIPCION, descripcion)
            put(WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_CATEGORIA_ID, categoriaId)
            put(WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_DIFICULTAD, dificultad)
        }
        return db.insert(WrestlingSqliteContract.ExerciseEntry.TABLE_NAME, null, values)
    }

    // Read
    fun getAllExercises(): List<String> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_NOMBRE)
        val cursor: Cursor = db.query(
            WrestlingSqliteContract.ExerciseEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val exercises = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                exercises.add(getString(getColumnIndexOrThrow(WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_NOMBRE)))
            }
        }
        cursor.close()
        return exercises
    }

    fun getExercisesByCategory(categoryName: String): List<String>  {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_NOMBRE)
        val selection = """
        ${WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_CATEGORIA_ID} = (
            SELECT ${WrestlingSqliteContract.CategoryEntry.COLUMN_NAME_ID}
            FROM ${WrestlingSqliteContract.CategoryEntry.TABLE_NAME}
            WHERE ${WrestlingSqliteContract.CategoryEntry.COLUMN_NAME_NOMBRE} = ?
        )
    """
        val selectionArgs = arrayOf(categoryName)

        val cursor: Cursor = db.query(
            WrestlingSqliteContract.ExerciseEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val exercises = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                exercises.add(getString(getColumnIndexOrThrow(WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_NOMBRE)))
            }
        }
        cursor.close()
        return exercises
    }

    suspend fun getExercisesForFisica(): List<String> {
        return getExercisesByCategory("Fuerza") +
                getExercisesByCategory("Cardio") +
                getExercisesByCategory("Flexibilidad")
    }


    // Update
    fun updateExercise(id: Long, nombre: String, descripcion: String, dificultad: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_NOMBRE, nombre)
            put(WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_DESCRIPCION, descripcion)
            put(WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_DIFICULTAD, dificultad)
        }
        val selection = "${WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.update(WrestlingSqliteContract.ExerciseEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    // Delete
    fun deleteExercise(id: Long): Int {
        val db = dbHelper.writableDatabase
        val selection = "${WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.delete(WrestlingSqliteContract.ExerciseEntry.TABLE_NAME, selection, selectionArgs)
    }
}
