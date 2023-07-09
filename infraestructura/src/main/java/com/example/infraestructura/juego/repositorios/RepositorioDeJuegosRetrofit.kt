package com.example.infraestructura.juego.repositorios

import com.example.dominio.juego.modelo.Juego
import com.example.dominio.juego.modelo.JuegoDetalle
import com.example.infraestructura.compartido.clienteHttp.ServicioDeJuego
import com.example.infraestructura.juego.anticorrupcion.TraductorDeJuegos
import com.example.infraestructura.juego.repositorios.contratos.RepositorioRemotoDeJuegos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositorioDeJuegosRetrofit @Inject constructor(private val servicioDeJuego: ServicioDeJuego) :
    RepositorioRemotoDeJuegos {

    override fun obtenerJuegos(): Flow<List<Juego>> {
        return flow { emit(servicioDeJuego.obtenerJuegos()) }.map { resultado ->
            resultado.map { juegoDto ->
                TraductorDeJuegos.desdeDtoHaciaModelo(
                    juegoDto
                )
            }
        }
    }

    override suspend fun obtenerJuego(identificador: Int): Flow<JuegoDetalle?> {
        return flow { emit(servicioDeJuego.obtenerJuegoPorIdentificador(identificador)) }.map { juegoDto ->
            TraductorDeJuegos.desdeDtoDetalleHaciaModelo(
                juegoDto
            )
        }
    }
}