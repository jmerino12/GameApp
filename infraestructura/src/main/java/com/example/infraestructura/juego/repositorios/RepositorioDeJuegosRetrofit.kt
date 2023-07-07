package com.example.infraestructura.juego.repositorios

import com.example.dominio.juego.modelo.Juego
import com.example.infraestructura.compartido.clienteHttp.ServicioDeJuego
import com.example.infraestructura.juego.anticorrupcion.TraductorDeJuegos
import com.example.infraestructura.juego.repositorios.contratos.RepositorioRemotoDeJuegos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RepositorioDeJuegosRetrofit constructor(private val servicioDeJuego: ServicioDeJuego) :
    RepositorioRemotoDeJuegos {

    override suspend fun obtenerJuegos(): Flow<List<Juego>> {
        return flow { emit(servicioDeJuego.obtenerJuegos()) }.map { resultado ->
            resultado.map { juegoDto ->
                TraductorDeJuegos.desdeDtoHaciaModelo(
                    juegoDto
                )
            }
        }
    }

    override suspend fun obtenerJuego(identificador: Int): Flow<Juego?> {
        TODO("Not yet implemented")
    }
}