package com.carlosgarciaalonso.wrestlingapp.data.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.carlosgarciaalonso.wrestlingapp.data.WrestlingSqliteContract
import com.carlosgarciaalonso.wrestlingapp.data.WrestlingSqliteHelper


class UserRepository(context: Context) {
    private val dbHelper = WrestlingSqliteHelper(context)

    // Create
    fun insertUser(nombre: String, correo: String, fechaRegistro: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(WrestlingSqliteContract.UserEntry.COLUMN_NAME_NOMBRE, nombre)
            put(WrestlingSqliteContract.UserEntry.COLUMN_NAME_CORREO, correo)
            put(WrestlingSqliteContract.UserEntry.COLUMN_NAME_FECHA_REGISTRO, fechaRegistro)
        }
        return db.insert(WrestlingSqliteContract.UserEntry.TABLE_NAME, null, values)
    }

    // Read
    fun getAllUsers(): List<String> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(WrestlingSqliteContract.UserEntry.COLUMN_NAME_NOMBRE)
        val cursor: Cursor = db.query(
            WrestlingSqliteContract.UserEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val users = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                users.add(getString(getColumnIndexOrThrow(WrestlingSqliteContract.UserEntry.COLUMN_NAME_NOMBRE)))
            }
        }
        cursor.close()
        return users
    }

    // Update
    fun updateUser(id: Long, nombre: String, correo: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(WrestlingSqliteContract.UserEntry.COLUMN_NAME_NOMBRE, nombre)
            put(WrestlingSqliteContract.UserEntry.COLUMN_NAME_CORREO, correo)
        }
        val selection = "${WrestlingSqliteContract.UserEntry.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.update(WrestlingSqliteContract.UserEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    // Delete
    fun deleteUser(id: Long): Int {
        val db = dbHelper.writableDatabase
        val selection = "${WrestlingSqliteContract.UserEntry.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.delete(WrestlingSqliteContract.UserEntry.TABLE_NAME, selection, selectionArgs)
    }
}
