package com.example.dominio.juego.repositorios

import com.example.dominio.juego.modelo.Juego
import kotlinx.coroutines.flow.Flow

interface RepositorioDeJuegos {
    fun obtenerJuegos(): Flow<List<Juego>>
}