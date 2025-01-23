package com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.carlosgarciaalonso.wrestlingapp.WrestlingApplication
import com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.WrestlingSqliteContract
import com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.WrestlingSqliteHelper

// Clase repositorio con las funciones necesarias para realizar el CRUD de la tabla de progreso
class ProgressRepository(context: Context) {
    private val dbHelper = (context.applicationContext as WrestlingApplication).dbHelper

    // Create (Insertar un nuevo progreso)
    fun insertProgress(userId: Long, ejercicioId: Long, fecha: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(WrestlingSqliteContract.ProgressEntry.COLUMN_NAME_USER_ID, userId)
            put(WrestlingSqliteContract.ProgressEntry.COLUMN_NAME_EJERCICIO_ID, ejercicioId)
            put(WrestlingSqliteContract.ProgressEntry.COLUMN_NAME_FECHA, fecha)
        }
        return db.insert(WrestlingSqliteContract.ProgressEntry.TABLE_NAME, null, values)
    }

    // Read (Obtener el progreso de un usuario específico)
    fun getProgressByUser(userId: Long): List<String> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            WrestlingSqliteContract.ProgressEntry.COLUMN_NAME_EJERCICIO_ID,
            WrestlingSqliteContract.ProgressEntry.COLUMN_NAME_FECHA
        )
        val selection = "${WrestlingSqliteContract.ProgressEntry.COLUMN_NAME_USER_ID} = ?"
        val selectionArgs = arrayOf(userId.toString())

        val cursor: Cursor = db.query(
            WrestlingSqliteContract.ProgressEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val progressList = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val ejercicioId = getLong(getColumnIndexOrThrow(WrestlingSqliteContract.ProgressEntry.COLUMN_NAME_EJERCICIO_ID))
                val fecha = getString(getColumnIndexOrThrow(WrestlingSqliteContract.ProgressEntry.COLUMN_NAME_FECHA))
                progressList.add("Ejercicio ID: $ejercicioId, Fecha: $fecha")
            }
        }
        cursor.close()
        return progressList
    }

    // Update (Actualizar la fecha de un progreso existente)
    fun updateProgress(id: Long, nuevaFecha: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(WrestlingSqliteContract.ProgressEntry.COLUMN_NAME_FECHA, nuevaFecha)
        }
        val selection = "${WrestlingSqliteContract.ProgressEntry.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.update(WrestlingSqliteContract.ProgressEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    // Delete (Eliminar un progreso específico)
    fun deleteProgress(id: Long): Int {
        val db = dbHelper.writableDatabase
        val selection = "${WrestlingSqliteContract.ProgressEntry.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.delete(WrestlingSqliteContract.ProgressEntry.TABLE_NAME, selection, selectionArgs)
    }
}
