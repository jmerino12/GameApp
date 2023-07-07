package com.example.infraestructura.juego.persistencia.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.infraestructura.juego.persistencia.entidad.JuegoEntidad
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoJuego {

    @Query("SELECT * FROM JuegoEntidad")
     fun obtenerJuegos(): Flow<List<JuegoEntidad>>

    @Query("SELECT * FROM JuegoEntidad WHERE identificador = :id")
     fun obtenerJuegoPorIdentificador(id: Int): Flow<JuegoEntidad?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarJuego(juego: JuegoEntidad)
}