package com.example.gameapp.ui.pantallas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dominio.juego.modelo.Juego
import com.example.dominio.juego.repositorios.RepositorioDeJuegos
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
class PantallaInicialViewModel @Inject constructor(
    private val repositorioDeJuegos: RepositorioDeJuegos
) : ViewModel() {

    private val cargando = MutableStateFlow(PantallaDeInicioEstadosDeUi().cargando)
    private val exito = MutableStateFlow(PantallaDeInicioEstadosDeUi().exito)
    private val error = MutableStateFlow(PantallaDeInicioEstadosDeUi().error)

    private val _estadoDeUi = MutableStateFlow(PantallaDeInicioEstadosDeUi())
    val estadoDeUi: StateFlow<PantallaDeInicioEstadosDeUi> get() = _estadoDeUi

    init {
        viewModelScope.launch {
            combine(
                cargando, exito, error
            ) { cargando, exito, error  ->
                PantallaDeInicioEstadosDeUi(cargando, exito, error)
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
            repositorioDeJuegos.obtenerJuegos().collect { juegos ->
                cargando.value = false
                exito.value = juegos
            }
        } catch (e: Exception) {
            Log.e("obtenerJuegoError", e.localizedMessage)
            cargando.value = false
        }
    }
}

data class PantallaDeInicioEstadosDeUi(
    var cargando: Boolean = true,
    var exito: List<Juego> = listOf(),
    var error: Boolean = false,
)