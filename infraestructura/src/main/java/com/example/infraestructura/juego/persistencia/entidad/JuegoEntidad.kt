package com.example.infraestructura.juego.persistencia.entidad

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JuegoEntidad(
    @PrimaryKey val identificador: Int,
    val titulo: String,
    val genero: String,
    val descripcion: String,
    val miniatura: String,
    val plataforma: String,
    val editor: String,
    val fechaDeLanzamiento: String,
)