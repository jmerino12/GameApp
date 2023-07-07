package com.example.infraestructura.juego.repositorios.contratos

import com.example.dominio.juego.modelo.Juego
import com.example.dominio.juego.repositorios.RepositorioDeJuegos

interface RepositorioLocalDeJuegos: RepositorioDeJuegos {
    suspend fun guardarJuego(juego: Juego)
}