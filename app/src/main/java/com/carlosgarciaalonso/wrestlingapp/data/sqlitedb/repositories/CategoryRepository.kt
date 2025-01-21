package com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.carlosgarciaalonso.wrestlingapp.WrestlingApplication
import com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.WrestlingSqliteContract
import com.carlosgarciaalonso.wrestlingapp.data.sqlitedb.WrestlingSqliteHelper

class CategoryRepository(context: Context) {
    private val dbHelper = (context.applicationContext as WrestlingApplication).dbHelper

    // Create
    fun insertCategory(nombre: String): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(WrestlingSqliteContract.CategoryEntry.COLUMN_NAME_NOMBRE, nombre)
        }
        return db.insert(WrestlingSqliteContract.CategoryEntry.TABLE_NAME, null, values)
    }

    // Read
    fun getAllCategories(): List<String> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(WrestlingSqliteContract.CategoryEntry.COLUMN_NAME_NOMBRE)
        val cursor: Cursor = db.query(
            WrestlingSqliteContract.CategoryEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val categories = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                categories.add(getString(getColumnIndexOrThrow(WrestlingSqliteContract.CategoryEntry.COLUMN_NAME_NOMBRE)))
            }
        }
        cursor.close()
        return categories
    }

    // Update
    fun updateCategory(id: Long, nombre: String): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(WrestlingSqliteContract.CategoryEntry.COLUMN_NAME_NOMBRE, nombre)
        }
        val selection = "${WrestlingSqliteContract.CategoryEntry.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.update(WrestlingSqliteContract.CategoryEntry.TABLE_NAME, values, selection, selectionArgs)
    }

    // Delete
    fun deleteCategory(id: Long): Int {
        val db = dbHelper.writableDatabase
        val selection = "${WrestlingSqliteContract.CategoryEntry.COLUMN_NAME_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        return db.delete(WrestlingSqliteContract.CategoryEntry.TABLE_NAME, selection, selectionArgs)
    }
}
