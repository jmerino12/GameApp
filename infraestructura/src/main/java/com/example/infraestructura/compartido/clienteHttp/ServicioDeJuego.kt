package com.example.infraestructura.compartido.clienteHttp

import com.example.infraestructura.juego.clienteHttp.dto.JuegoDto
import retrofit2.http.GET

interface ServicioDeJuego {
    @GET("/games")
    suspend fun obtenerJuegos(): List<JuegoDto>
}