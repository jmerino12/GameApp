package com.example.infraestructura.juego.repositorios.contratos

import com.example.dominio.juego.modelo.Juego
import com.example.dominio.juego.repositorios.RepositorioDeJuegos
import kotlinx.coroutines.flow.Flow

interface RepositorioLocalDeJuegos : RepositorioDeJuegos {
    suspend fun guardarJuego(juego: Juego)
    suspend fun eliminarJuego(juego: Juego)
    suspend fun esUnJuegoFavorito(identificador: Int): Flow<Boolean>
}