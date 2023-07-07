package com.example.infraestructura.juego.repositorios

import com.example.dominio.juego.modelo.Juego
import com.example.infraestructura.juego.anticorrupcion.TraductorDeJuegos
import com.example.infraestructura.juego.persistencia.dao.DaoJuego
import com.example.infraestructura.juego.repositorios.contratos.RepositorioLocalDeJuegos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositorioDeJuegosRoom @Inject constructor(private val daoJuego: DaoJuego) :
    RepositorioLocalDeJuegos {

    override suspend fun guardarJuego(juego: Juego) {
        daoJuego.insertarJuego(TraductorDeJuegos.desdeModeloHaciaEntidad(juego))
    }

    override suspend fun obtenerJuegos(): Flow<List<Juego>> {
        return daoJuego.obtenerJuegos()
            .map { juegos -> juegos.map { juego -> TraductorDeJuegos.desdeEntidadHaciaModelo(juego) } }
    }

    override suspend fun obtenerJuego(identificador: Int): Flow<Juego?> {
        return daoJuego.obtenerJuegoPorIdentificador(identificador)
            .map { juego ->
                if (juego != null) {
                    TraductorDeJuegos.desdeEntidadHaciaModelo(juego)
                } else {
                    null
                }
            }
    }
}