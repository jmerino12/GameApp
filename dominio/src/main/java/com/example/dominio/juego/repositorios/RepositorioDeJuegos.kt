package com.example.dominio.juego.repositorios

import com.example.dominio.juego.modelo.Juego
import kotlinx.coroutines.flow.Flow

interface RepositorioDeJuegos {
    suspend fun obtenerJuegos(): Flow<List<Juego>>
}