package com.example.infraestructura.juego.repositorios

import com.example.dominio.juego.modelo.Juego
import com.example.dominio.juego.modelo.JuegoDetalle
import com.example.dominio.juego.repositorios.RepositorioDeJuegos
import com.example.infraestructura.compartido.VerificadorDeInternet
import com.example.infraestructura.compartido.clienteHttp.excepciones.ExcepcionDeInternet
import com.example.infraestructura.juego.repositorios.contratos.RepositorioLocalDeJuegos
import com.example.infraestructura.juego.repositorios.contratos.RepositorioRemotoDeJuegos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ProxyDeJuego @Inject constructor(
    private val repositorioLocalDeJuegos: RepositorioLocalDeJuegos,
    private val repositorioRemotoDeJuegos: RepositorioRemotoDeJuegos,
    private val verificadorDeInternet: VerificadorDeInternet,
) : RepositorioDeJuegos {

    override fun obtenerJuegos(): Flow<List<Juego>> {
        return if (verificadorDeInternet.hayConexionInternet()) {
            combine(
                repositorioLocalDeJuegos.obtenerJuegos(),
                repositorioRemotoDeJuegos.obtenerJuegos()
            ) { local, remoto ->
                local + remoto
            }
        } else {
            repositorioLocalDeJuegos.obtenerJuegos()
        }
    }

    suspend fun obtenerJuego(identificador: Int): Flow<JuegoDetalle?> {
        return if (verificadorDeInternet.hayConexionInternet()) {
            repositorioRemotoDeJuegos.obtenerJuego(identificador)
        } else {
            throw ExcepcionDeInternet()
        }
    }
}