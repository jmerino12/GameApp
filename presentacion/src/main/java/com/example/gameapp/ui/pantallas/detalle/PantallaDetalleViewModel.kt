package com.example.gameapp.ui.pantallas.detalle

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dominio.juego.modelo.JuegoDetalle
import com.example.gameapp.R
import com.example.infraestructura.compartido.clienteHttp.excepciones.ExcepcionDeInternet
import com.example.infraestructura.juego.anticorrupcion.TraductorDeJuegos
import com.example.infraestructura.juego.repositorios.ProxyDeJuego
import com.example.infraestructura.juego.repositorios.contratos.RepositorioLocalDeJuegos
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
    private val repositorioLocalDeJuegos: RepositorioLocalDeJuegos,
    private val application: Application
) :
    AndroidViewModel(application) {

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
            error.value = true
            if(e is ExcepcionDeInternet)  mensajeDeError.value = e.message else {
                mensajeDeError.value = application.getString(R.string.vuelve_a_intentarlo)
            }
            cargando.value = false
        }
    }

    fun mensajeMostrado() {
        mensajeDeError.value = null
    }

    private fun mostrarMensaje(message: String) {
        mensajeDeError.value = message
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
                mostrarMensaje(application.getString(R.string.juego_marcado_como_favorito))
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
                mostrarMensaje(application.getString(R.string.juego_eliminado_de_favorito))
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
    var mensajeDeError: String? = null,
)