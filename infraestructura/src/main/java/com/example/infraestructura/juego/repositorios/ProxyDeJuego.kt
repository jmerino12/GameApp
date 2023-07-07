package com.example.infraestructura.juego.repositorios

import com.example.dominio.juego.modelo.Juego
import com.example.dominio.juego.repositorios.RepositorioDeJuegos
import com.example.infraestructura.juego.repositorios.contratos.RepositorioLocalDeJuegos
import com.example.infraestructura.juego.repositorios.contratos.RepositorioRemotoDeJuegos
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProxyDeJuego @Inject constructor(
    private val repositorioLocalDeJuegos: RepositorioLocalDeJuegos,
    private val repositorioRemotoDeJuegos: RepositorioRemotoDeJuegos
): RepositorioDeJuegos {

    override suspend fun obtenerJuegos(): Flow<List<Juego>> {
        return  repositorioRemotoDeJuegos.obtenerJuegos()
    }

    override suspend fun obtenerJuego(identificador: Int): Flow<Juego?> {
        return repositorioRemotoDeJuegos.obtenerJuego(identificador)
    }
}