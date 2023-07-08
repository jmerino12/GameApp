package com.example.infraestructura.juego.persistencia.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.dominio.juego.modelo.JuegoDetalle
import com.example.infraestructura.juego.persistencia.entidad.JuegoEntidad
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoJuego {

    @Query("SELECT * FROM JuegoEntidad")
     fun obtenerJuegos(): Flow<List<JuegoEntidad>>

    @Query("SELECT * FROM JuegoEntidad WHERE identificador = :id LIMIT 1")
    fun obtenerJuego(id : Int) : Flow<JuegoEntidad?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarJuego(juego: JuegoEntidad)

    @Delete
    suspend fun eliminarJuego(juego: JuegoEntidad)
}