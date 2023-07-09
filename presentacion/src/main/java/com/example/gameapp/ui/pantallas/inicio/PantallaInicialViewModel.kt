package com.example.gameapp.ui.pantallas.inicio

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PantallaInicialViewModel @Inject constructor(
    private val repositorioDeJuegos: RepositorioDeJuegos,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val cargando = MutableStateFlow(PantallaDeInicioEstadosDeUi().cargando)
    private val juegosDeApi = MutableStateFlow(PantallaDeInicioEstadosDeUi().juegoDeApi)
    private val juegosFavoritos = MutableStateFlow(PantallaDeInicioEstadosDeUi().juegosFavoritos)
    private val juegosOriginales = MutableStateFlow(PantallaDeInicioEstadosDeUi().juegosOriginales)
    private val generos = MutableStateFlow(PantallaDeInicioEstadosDeUi().generos)
    private val editores = MutableStateFlow(PantallaDeInicioEstadosDeUi().editores)
    private val error = MutableStateFlow(PantallaDeInicioEstadosDeUi().error)
    private val filtro = savedStateHandle.getStateFlow(
        "TASKS_FILTER_SAVED_STATE_KEY",
        TasksFilterType.TODOS_LOS_JUEGOS
    )

    private val _estadoDeUi = MutableStateFlow(PantallaDeInicioEstadosDeUi())
    val estadoDeUi: StateFlow<PantallaDeInicioEstadosDeUi> get() = _estadoDeUi

    var valorDelFiltro = MutableStateFlow("")

    init {
        viewModelScope.launch {
            combine(
                cargando,
                juegosDeApi,
                juegosFavoritos,
                juegosOriginales,
                generos,
                editores,
                filtro,
                error
            ) { argumentos ->
                PantallaDeInicioEstadosDeUi(
                    argumentos[0] as Boolean,
                    argumentos[1] as MutableList<Juego>,
                    argumentos[2] as MutableList<Juego>,
                    argumentos[3] as List<Juego>,
                    argumentos[4] as MutableList<String>,
                    argumentos[5] as MutableList<String>,
                    argumentos[6] as TasksFilterType,
                    argumentos[7] as Boolean,
                )
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
                juegosOriginales.value = juegos.distinctBy { it.identificador }
                juegos.map {
                    if (it.favorito) juegosFavoritos.value.add(it) else {
                        juegosDeApi.value.add(it)
                    }
                }
                aplicarFiltroInicial()
                generos.value = obtenerListaDeGeneros(juegos).toMutableList()
                editores.value = obtenerListaDeEditores(juegos).toMutableList()
            }
        } catch (e: Exception) {
            Log.e("obtenerJuegoError", e.localizedMessage)
            cargando.value = false
        }
    }

    private fun obtenerListaDeGeneros(juegos: List<Juego>): List<String> {
        val generos = mutableSetOf<String>()
        juegos.forEach { juego ->
            generos.add(juego.genero)
        }
        val listaGeneros = generos.toList()
        val listaOrdenada = listaGeneros.sorted().toMutableList()
        listaOrdenada.add(0, "Todos")
        return listaOrdenada
    }

    private fun obtenerListaDeEditores(juegos: List<Juego>): List<String> {
        val editores = mutableSetOf<String>()
        juegos.forEach { juego ->
            editores.add(juego.editor.trim())
        }
        return editores.toList().sorted()
    }

    private fun aplicarFiltroInicial() {
        val estadoActual = _estadoDeUi.value
        val juegosFiltrados =
            filtrarJuegos(estadoActual.juegoDeApi, estadoActual.filtro, valorDelFiltro.value)
        _estadoDeUi.value = estadoActual.copy(juegoDeApi = juegosFiltrados.toMutableList())
    }

    fun seleccionarFiltro(requestType: TasksFilterType, valorDeMiFiltro: String?) {
        val estadoActual = _estadoDeUi.value
        if (estadoActual.filtro != requestType || valorDeMiFiltro != valorDelFiltro.value) {
            val juegosFiltrados =
                filtrarJuegos(estadoActual.juegosOriginales, requestType, valorDeMiFiltro)
            _estadoDeUi.value = estadoActual.copy(
                juegoDeApi = juegosFiltrados.toMutableList(),
                filtro = requestType
            )
        }
    }


    private fun filtrarJuegos(
        juegos: List<Juego>,
        filtro: TasksFilterType,
        tipo: String?
    ): List<Juego> {
        actualizarMiFiltro(tipo)
        return when (filtro) {
            TasksFilterType.TODOS_LOS_JUEGOS -> juegos
            TasksFilterType.JUEGOS_POR_GENERO -> juegos.filter { it.genero == tipo }
            TasksFilterType.JUEGOS_POR_EDITOR -> juegos.filter { it.editor == tipo }
        }
    }

    private fun actualizarMiFiltro(tipo: String?) {
        valorDelFiltro.value = tipo ?: "Todos"
    }

}

data class PantallaDeInicioEstadosDeUi(
    var cargando: Boolean = true,
    var juegoDeApi: MutableList<Juego> = mutableListOf(),
    var juegosFavoritos: MutableList<Juego> = mutableListOf(),
    var juegosOriginales: List<Juego> = listOf(),
    var generos: MutableList<String> = mutableListOf(),
    var editores: MutableList<String> = mutableListOf(),
    var filtro: TasksFilterType = TasksFilterType.TODOS_LOS_JUEGOS,
    var error: Boolean = false,
)

enum class TasksFilterType {
    TODOS_LOS_JUEGOS,
    JUEGOS_POR_GENERO,
    JUEGOS_POR_EDITOR,
}
