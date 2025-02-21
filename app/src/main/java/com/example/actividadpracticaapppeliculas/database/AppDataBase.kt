package com.example.actividadpracticaapppeliculas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.actividadpracticaapppeliculas.dao.FavoriteMovieDAO
import com.example.actividadpracticaapppeliculas.dao.UserDao
import com.example.actividadpracticaapppeliculas.model.FavoriteMovie
import com.example.actividadpracticaapppeliculas.model.User

@Database(entities = [User::class, FavoriteMovie::class], version = 2, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract  fun userDao(): UserDao
    abstract fun favoriteMovieDao(): FavoriteMovieDAO
    companion object {
        // La variable INSTANCE es marcada como @Volatile para garantizar que los cambios sean visibles para todos los hilos.
        @Volatile
        private var INSTANCE: AppDataBase? = null

        // Este métoo se encarga de devolver la instancia única de la base de datos.
        // Se usa synchronized para asegurarse de que solo un hilo pueda crear la instancia al mismo tiempo.
        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                // Se crea la instancia de la base de datos usando Room.databaseBuilder.
                val instance = Room.databaseBuilder(
                    context.applicationContext,  // El contexto de la aplicación para evitar fugas de memoria.
                    AppDataBase::class.java,      // La clase de la base de datos (nuestra clase abstracta).
                    "app_database"                 // El nombre de la base de datos.
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance  // Devuelve la instancia recién creada.
            }
        }
    }

}