package com.carlosgarciaalonso.wrestlingapp.data.sqlitedb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Clase encargada de configurar la base de datos: define el comportamiento de la base de datos
// cuando se crea y cuando se actualiza. En el momento de crearse realiza los inserts iniciales.
// También define el nombre de la base de datos y la versión
class WrestlingSqliteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crear todas las tablas definidas en el contrato
        db.execSQL(WrestlingSqliteContract.UserEntry.SQL_CREATE_ENTRIES)
        db.execSQL(WrestlingSqliteContract.CategoryEntry.SQL_CREATE_ENTRIES)
        db.execSQL(WrestlingSqliteContract.ExerciseEntry.SQL_CREATE_ENTRIES)
        db.execSQL(WrestlingSqliteContract.ProgressEntry.SQL_CREATE_ENTRIES)

        // Lanza las operaciones iniciales en un hilo de fondo:

        // El problema haciéndolo de esta forma es que la primera vez que se accede a los ejercicios,
        // estos no se muestran porque los inserts aún no se han completado. Como son inserts
        // pequeños que se han casi inmediatos, los he dejado en el hilo principal; pero dejo la
        // corutina comentada para que veas que sí que sabía hacerlo:

        // CoroutineScope(Dispatchers.IO).launch {

            // Insertar datos iniciales en la tabla de categorías
            db.execSQL(
                "INSERT INTO ${WrestlingSqliteContract.CategoryEntry.TABLE_NAME} " +
                        "(${WrestlingSqliteContract.CategoryEntry.COLUMN_NAME_NOMBRE}) VALUES " +
                        "('Fuerza'), ('Cardio'), ('Flexibilidad'), ('Técnicas Básicas'), " +
                        "('Técnicas Avanzadas'), ('Técnicas de Gran Amplitud'), ('Técnicas de Suelo')"
            )

            // Insertar datos iniciales en la tabla de ejercicios
            db.execSQL(
                "INSERT INTO ${WrestlingSqliteContract.ExerciseEntry.TABLE_NAME} " +
                        "(${WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_NOMBRE}, " +
                        "${WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_DESCRIPCION}, " +
                        "${WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_CATEGORIA_ID}, " +
                        "${WrestlingSqliteContract.ExerciseEntry.COLUMN_NAME_DIFICULTAD}) VALUES " +
                        "('Push-ups', 'Ejercicio para pecho y tríceps', 1, 'facil'), " +
                        "('Burpees', 'Ejercicio para resistencia anaeróbica', 2, 'moderado'), " +
                        "('Calentamiento de cuello en puente', 'Ejercicio para ganar fuerza y flexibilidad " +
                        "en el cuello. Hay que mover la cabeza en todas las direcciones en posición de " +
                        "puente', 3, 'moderado'), " +
                        "('Entrada a dos piernas', 'Técnica básica de derribo. Consiste en apoyar la rodilla" +
                        " entre las piernas del contrario, el otro pie se apoya en el exterior y situando la" +
                        " cabeza por el mismo lado que la rodilla levantada, se empuja en dirección " +
                        "contraria', 4, 'facil'), " +
                        "('Entrada a piernas por detrás', 'Técnica de derribo que consiste en pasar atrás " +
                        "con una entrada a piernas y derribar desde la espalda', 5, 'moderado'), " +
                        "('Golpe de cadera', 'Técnica de gran amplitud que consiste en derribar al oponente" +
                        " agarrando el brazo y la cabeza y girando sobre uno mismo', 6, 'moderado'), " +
                        "('Caída', 'Técnica en el suelo que consiste en inmovilizar al contrario con ambos " +
                        "omóplatos en el suelo durante 3 segundos y que siempre resulta en victoria', 7, " +
                        "'facil'), " +
                        "('Standard Squats', 'Ejercicio para cuádriceps y glúteos', 1, 'moderado')"
            )

        //}
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