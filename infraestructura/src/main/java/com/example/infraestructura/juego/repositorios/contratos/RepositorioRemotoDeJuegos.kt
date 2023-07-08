package com.example.infraestructura.juego.repositorios.contratos

import com.example.dominio.juego.modelo.Juego
import com.example.dominio.juego.repositorios.RepositorioDeJuegos
import kotlinx.coroutines.flow.Flow

interface RepositorioRemotoDeJuegos : RepositorioDeJuegos {
    suspend fun obtenerJuego(identificador: Int): Flow<Juego?>
}