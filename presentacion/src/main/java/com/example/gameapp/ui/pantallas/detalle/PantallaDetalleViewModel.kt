package com.example.gameapp.ui.pantallas.detalle

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dominio.juego.modelo.JuegoDetalle
import com.example.infraestructura.juego.repositorios.contratos.RepositorioRemotoDeJuegos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PantallaDetalleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repositorioRemotoDeJuegos: RepositorioRemotoDeJuegos
) :
    ViewModel() {

    private val cargando = MutableStateFlow(PantallaDetalleEstadosDeUi().cargando)
    private val exito = MutableStateFlow(PantallaDetalleEstadosDeUi().exito)
    private val error = MutableStateFlow(PantallaDetalleEstadosDeUi().error)

    private val _estadoDeUi = MutableStateFlow(PantallaDetalleEstadosDeUi())
    val estadoDeUi: StateFlow<PantallaDetalleEstadosDeUi> get() = _estadoDeUi

    private val juegoId: Int = checkNotNull(savedStateHandle["juego"])

    init {
        viewModelScope.launch {
            combine(
                cargando, exito, error
            ) { cargando, exito, error ->
                PantallaDetalleEstadosDeUi(cargando, exito, error)
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
            repositorioRemotoDeJuegos.obtenerJuego(juegoId).collect { juegos ->
                cargando.value = false
                exito.value = juegos
            }
        } catch (e: Exception) {
            Log.e("obtenerJuegoError", e.localizedMessage)
            cargando.value = false
        }
    }
}

data class PantallaDetalleEstadosDeUi(
    var cargando: Boolean = true,
    var exito: JuegoDetalle? = null,
    var error: Boolean = false,
)