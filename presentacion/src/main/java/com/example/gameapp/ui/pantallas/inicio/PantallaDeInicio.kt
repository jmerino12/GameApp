package com.example.gameapp.ui.pantallas.inicio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dominio.juego.modelo.Juego
import com.example.gameapp.ui.compatido.CargandoContenido

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDeInicio(
    modifier: Modifier = Modifier,
    estadoDeUi: PantallaDeInicioEstadosDeUi,
    valorDelFiltro: String,
    filtroDeGenero: (String) -> Unit,
    filtroDeEditor: (String) -> Unit,
    resetearFiltro: () -> Unit,
    juegoClick: (Int) -> Unit,
) {
    Scaffold { paddingValues ->
        ContenidoDePantallaDeInicio(
            modifier = modifier.padding(paddingValues),
            estadoDeUi.cargando,
            estadoDeUi.juegoDeApi,
            estadoDeUi.juegosFavoritos,
            estadoDeUi.generos,
            estadoDeUi.editores,
            valorDelFiltro,
            filtroDeGenero,
            filtroDeEditor,
            resetearFiltro,
            juegoClick,
        )
    }
}


@Composable
fun ContenidoDePantallaDeInicio(
    modifier: Modifier,
    cargando: Boolean,
    juegosDeApi: List<Juego>,
    juegosFavoritos: List<Juego>,
    generosDeJuego: List<String>,
    editores: List<String>,
    valorDelFiltro: String,
    filtroDeGenero: (String) -> Unit,
    filtroDeEditor: (String) -> Unit,
    resetearFiltro: () -> Unit,

    juegoClick: (Int) -> Unit,
) {
    CargandoContenido(
        cargando = cargando
    ) {
        Column {
            FiltroPor("Generos", generosDeJuego, valorDelFiltro, filtroDeGenero, resetearFiltro)
            FiltroPor("Editores", editores, valorDelFiltro, filtroDeEditor, resetearFiltro)

            if (juegosDeApi.isNotEmpty()) {
                ListaDeJuegos(
                    juegoClick = juegoClick,
                    juegos = juegosDeApi,
                    tipoDeContenido = "Juego desde Api"
                )
            }

            if (juegosFavoritos.isNotEmpty()) {
                ListaDeJuegos(
                    juegoClick = juegoClick,
                    juegos = juegosFavoritos,
                    tipoDeContenido = "Juegos Favoritos"
                )
            } else if (juegosDeApi.isEmpty() && juegosFavoritos.isEmpty()) {
                Text(text = "No tenemos nada para mostrar")
            }

        }

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun FiltroPor(
    tipoDeFiltro: String,
    filtros: List<String>,
    valorDelFiltro: String,
    seleccionarFiltro: (String) -> Unit,
    resetearFiltro: () -> Unit,

    ) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            Text(text = tipoDeFiltro)
        }
        items(filtros) {
            var estaSeleccionado = valorDelFiltro == it
            val leadingIcon: @Composable () -> Unit = { Icon(Icons.Default.Check, null) }
            FilterChip(
                selected = estaSeleccionado,
                onClick = {
                    if (it == "Todos") resetearFiltro() else seleccionarFiltro(it)
                },
                label = { Text(it) },
                leadingIcon = if (estaSeleccionado) leadingIcon else null
            )
        }
    }
}

@Composable
private fun ListaDeJuegos(
    modifier: Modifier = Modifier,
    tipoDeContenido: String,
    juegoClick: (Int) -> Unit,
    juegos: List<Juego>
) {
    LazyColumn {
        item {
            Text(text = tipoDeContenido)
        }
        items(juegos) {
            Row(modifier = Modifier.clickable { juegoClick(it.identificador) }) {
                AsyncImage(model = it.miniatura, contentDescription = it.titulo)
                Column {
                    Text(text = it.titulo)
                    Text(text = it.descripcionCorta)
                }
            }
        }
    }
}
