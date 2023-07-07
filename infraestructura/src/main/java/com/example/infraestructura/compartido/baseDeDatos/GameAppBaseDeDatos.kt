package com.example.infraestructura.compartido.baseDeDatos

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.infraestructura.juego.persistencia.dao.DaoJuego
import com.example.infraestructura.juego.persistencia.entidad.JuegoEntidad

@Database(entities = [JuegoEntidad::class], version = 1)
abstract class GameAppBaseDeDatos: RoomDatabase() {
    abstract fun daoJuego(): DaoJuego
}