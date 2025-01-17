package com.carlosgarciaalonso.wrestlingapp.data

import android.provider.BaseColumns

object WrestlingSqliteContract {

    // Tabla de usuarios
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NOMBRE = "nombre"
        const val COLUMN_NAME_CORREO = "correo"
        const val COLUMN_NAME_FECHA_REGISTRO = "fecha_registro"

        const val SQL_CREATE_ENTRIES = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME_NOMBRE TEXT NOT NULL,
                $COLUMN_NAME_CORREO TEXT UNIQUE NOT NULL,
                $COLUMN_NAME_FECHA_REGISTRO TEXT NOT NULL
            )
        """
        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    // Tabla de categorías
    object CategoryEntry : BaseColumns {
        const val TABLE_NAME = "categorias"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NOMBRE = "nombre"
        const val COLUMN_NAME_DESCRIPCION = "descripcion"

        const val SQL_CREATE_ENTRIES = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME_NOMBRE TEXT NOT NULL,
                $COLUMN_NAME_DESCRIPCION TEXT NULL
            )
        """
        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    // Tabla de ejercicios
    object ExerciseEntry : BaseColumns {
        const val TABLE_NAME = "ejercicios"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NOMBRE = "nombre"
        const val COLUMN_NAME_DESCRIPCION = "descripcion"
        const val COLUMN_NAME_CATEGORIA_ID = "categoria_id"
        const val COLUMN_NAME_DIFICULTAD = "dificultad"

        const val SQL_CREATE_ENTRIES = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME_NOMBRE TEXT NOT NULL,
                $COLUMN_NAME_DESCRIPCION TEXT,
                $COLUMN_NAME_CATEGORIA_ID INTEGER,
                $COLUMN_NAME_DIFICULTAD TEXT NOT NULL CHECK($COLUMN_NAME_DIFICULTAD IN ('facil', 'moderado', 'dificil')),
                FOREIGN KEY ($COLUMN_NAME_CATEGORIA_ID) REFERENCES ${CategoryEntry.TABLE_NAME}(${CategoryEntry.COLUMN_NAME_ID})
            )
        """
        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    // Tabla de progreso
    object ProgressEntry : BaseColumns {
        const val TABLE_NAME = "progreso"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_USER_ID = "user_id"
        const val COLUMN_NAME_EJERCICIO_ID = "ejercicio_id"
        const val COLUMN_NAME_FECHA = "fecha"

        const val SQL_CREATE_ENTRIES = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME_USER_ID INTEGER NOT NULL,
                $COLUMN_NAME_EJERCICIO_ID INTEGER NOT NULL,
                $COLUMN_NAME_FECHA TEXT NOT NULL,
                FOREIGN KEY ($COLUMN_NAME_USER_ID) REFERENCES ${UserEntry.TABLE_NAME}(${UserEntry.COLUMN_NAME_ID}),
                FOREIGN KEY ($COLUMN_NAME_EJERCICIO_ID) REFERENCES ${ExerciseEntry.TABLE_NAME}(${ExerciseEntry.COLUMN_NAME_ID})
            )
        """
        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
