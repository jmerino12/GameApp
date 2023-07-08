package com.example.gameapp.ui.pantallas.detalle

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dominio.juego.modelo.JuegoDetalle
import com.example.infraestructura.juego.anticorrupcion.TraductorDeJuegos
import com.example.infraestructura.juego.repositorios.ProxyDeJuego
import com.example.infraestructura.juego.repositorios.contratos.RepositorioLocalDeJuegos
import com.example.infraestructura.juego.repositorios.contratos.RepositorioRemotoDeJuegos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PantallaDetalleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val proxyDeJuego: ProxyDeJuego,
    private val repositorioLocalDeJuegos: RepositorioLocalDeJuegos
) :
    ViewModel() {

    private val cargando = MutableStateFlow(PantallaDetalleEstadosDeUi().cargando)
    private val exito = MutableStateFlow(PantallaDetalleEstadosDeUi().exito)
    private val error = MutableStateFlow(PantallaDetalleEstadosDeUi().error)
    private val mensajeDeError = MutableStateFlow(PantallaDetalleEstadosDeUi().mensajeDeError)

    private val _estadoDeUi = MutableStateFlow(PantallaDetalleEstadosDeUi())
    val estadoDeUi: StateFlow<PantallaDetalleEstadosDeUi> get() = _estadoDeUi

    private val juegoId: Int = checkNotNull(savedStateHandle["juego"])

    init {
        viewModelScope.launch {
            combine(
                cargando, exito, error, mensajeDeError
            ) { cargando, exito, error, mensajeDeError ->
                PantallaDetalleEstadosDeUi(cargando, exito, error, mensajeDeError)
            }.catch { throwable ->
                throw throwable
            }.collect {
                _estadoDeUi.value = it
            }
        }
        viewModelScope.launch { obtenerJuego() }
    }


    private suspend fun obtenerJuego() = withContext(Dispatchers.IO) {
        cargando.value = true
        try {
            combine(
                proxyDeJuego.obtenerJuego(juegoId),
                repositorioLocalDeJuegos.esUnJuegoFavorito(juegoId)
            ) { juego, favorito ->
                Pair(juego, favorito)
            }.collect { (juego, favorito) ->
                cargando.value = false
                exito.value = juego?.copy(favorito = favorito)
            }
        } catch (e: Exception) {
            Log.e("obtenerJuegoError", e.localizedMessage)
            error.value = true
            mensajeDeError.value = e.localizedMessage
            cargando.value = false
        }
    }

    fun marcarComoFavorito() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositorioLocalDeJuegos.guardarJuego(
                    TraductorDeJuegos.desdeJuegoDetalleAJuego(
                        exito.value!!
                    )
                )
                exito.value = exito.value!!.copy(favorito = true)
            } catch (e: Exception) {
                Log.e("obtenerJuegoError", e.localizedMessage)
            }
        }
    }

    fun eliminarDeFavorito() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositorioLocalDeJuegos.eliminarJuego(
                    TraductorDeJuegos.desdeJuegoDetalleAJuego(
                        exito.value!!
                    )
                )
                exito.value = exito.value!!.copy(favorito = false)
            } catch (e: Exception) {
                Log.e("obtenerJuegoError", e.localizedMessage)
            }
        }
    }

}

data class PantallaDetalleEstadosDeUi(
    var cargando: Boolean = true,
    var exito: JuegoDetalle? = null,
    var error: Boolean = false,
    var mensajeDeError: String = "",
)