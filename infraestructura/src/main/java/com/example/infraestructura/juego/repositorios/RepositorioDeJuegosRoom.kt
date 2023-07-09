package com.example.infraestructura.juego.repositorios

import com.example.dominio.juego.modelo.Juego
import com.example.infraestructura.juego.anticorrupcion.TraductorDeJuegos
import com.example.infraestructura.juego.persistencia.dao.DaoJuego
import com.example.infraestructura.juego.repositorios.contratos.RepositorioLocalDeJuegos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositorioDeJuegosRoom @Inject constructor(private val daoJuego: DaoJuego) :
    RepositorioLocalDeJuegos {

    override suspend fun guardarJuego(juego: Juego): Unit = with(juego) {
        copy(favorito = !juego.favorito).also {
            daoJuego.insertarJuego(
                TraductorDeJuegos.desdeModeloHaciaEntidad(
                    it
                )
            )
        }
    }

    override suspend fun eliminarJuego(juego: Juego) {
        daoJuego.eliminarJuego(TraductorDeJuegos.desdeModeloHaciaEntidad(juego))
    }

    override suspend fun esUnJuegoFavorito(identificador: Int): Flow<Boolean> {
        val result = daoJuego.obtenerJuego(identificador).firstOrNull()
        return if (result != null) {
            flow { emit(true) }
        } else {
            flow { emit(false) }
        }
    }

    override fun obtenerJuegos(): Flow<List<Juego>> {
        return daoJuego.obtenerJuegos()
            .map { juegos -> juegos.map { juego -> TraductorDeJuegos.desdeEntidadHaciaModelo(juego) } }
    }
}