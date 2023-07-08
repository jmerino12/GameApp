package com.example.gameapp.ui.pantallas.inicio

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
    private val juegosDeApi = MutableStateFlow(PantallaDeInicioEstadosDeUi().juegoDeApi)
    private val juegosFavoritos = MutableStateFlow(PantallaDeInicioEstadosDeUi().juegosFavoritos)
    private val error = MutableStateFlow(PantallaDeInicioEstadosDeUi().error)

    private val _estadoDeUi = MutableStateFlow(PantallaDeInicioEstadosDeUi())
    val estadoDeUi: StateFlow<PantallaDeInicioEstadosDeUi> get() = _estadoDeUi

    init {
        viewModelScope.launch {
            combine(
                cargando, juegosDeApi, juegosFavoritos, error
            ) { cargando, juegosDeApi, juegosFavoritos, error ->
                PantallaDeInicioEstadosDeUi(cargando, juegosDeApi, juegosFavoritos, error)
            }.catch { throwable ->
                throw throwable
            }.collect {
                _estadoDeUi.value = it
            }
        }
        viewModelScope.launch { obtenerJuegos() }
    }

    private suspend fun obtenerJuegos() = withContext(Dispatchers.IO) {
        cargando.value = true
        try {
            repositorioDeJuegos.obtenerJuegos().collect { juegos ->
                cargando.value = false
                juegos.map {
                    if (it.favorito) juegosFavoritos.value.add(it) else {
                        juegosDeApi.value.add(it)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("obtenerJuegoError", e.localizedMessage)
            cargando.value = false
        }
    }
}

data class PantallaDeInicioEstadosDeUi(
    var cargando: Boolean = true,
    var juegoDeApi: MutableList<Juego> = mutableListOf(),
    var juegosFavoritos: MutableList<Juego> = mutableListOf(),
    var error: Boolean = false,
)