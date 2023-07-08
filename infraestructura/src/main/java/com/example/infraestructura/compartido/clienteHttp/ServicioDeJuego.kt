package com.example.infraestructura.compartido.clienteHttp

import com.example.infraestructura.juego.clienteHttp.dto.JuegoDetalleDto
import com.example.infraestructura.juego.clienteHttp.dto.JuegoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicioDeJuego {
    @GET("games")
    suspend fun obtenerJuegos(): List<JuegoDto>

    @GET("game")
    suspend fun obtenerJuegoPorIdentificador(
        @Query("id") identificador: Int,
    ): JuegoDetalleDto
}