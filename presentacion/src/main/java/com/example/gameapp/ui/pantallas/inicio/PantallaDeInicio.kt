package com.example.gameapp.ui.pantallas.inicio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.dominio.juego.modelo.Juego
import com.example.gameapp.R
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
    ordenar: () -> Unit,
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
            ordenar,
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
    ordenar: () -> Unit,

    juegoClick: (Int) -> Unit,
) {
    CargandoContenido(
        cargando = cargando
    ) {

        LazyColumn {
            item {
                FiltroPor(
                    stringResource(R.string.generos),
                    generosDeJuego,
                    valorDelFiltro,
                    filtroDeGenero,
                    resetearFiltro
                )
            }
            item { FiltroPor(stringResource(R.string.editores), editores, valorDelFiltro, filtroDeEditor, resetearFiltro) }

            if (juegosDeApi.isNotEmpty()) {
                ListaDeJuegos(
                    juegoClick = juegoClick,
                    juegos = juegosDeApi,
                    tipoDeContenido = R.string.juego_desde_api,
                    visualizarBotonDeOrdenar = true,
                    ordenar = ordenar
                )
            }
            if (juegosFavoritos.isNotEmpty()) {

                ListaDeJuegos(
                    juegoClick = juegoClick,
                    juegos = juegosFavoritos,
                    tipoDeContenido = R.string.juegos_favoritos,
                    visualizarBotonDeOrdenar = false,
                    ordenar = {}
                )
            } else if (juegosDeApi.isEmpty() && juegosFavoritos.isEmpty()) {
                item { Text(text = stringResource(R.string.no_tenemos_nada_para_mostrar)) }
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

private fun LazyListScope.ListaDeJuegos(
    modifier: Modifier = Modifier,
    tipoDeContenido: Int,
    juegoClick: (Int) -> Unit,
    juegos: List<Juego>,
    ordenar: () -> Unit,
    visualizarBotonDeOrdenar: Boolean
) {

    item {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = tipoDeContenido))
            if (visualizarBotonDeOrdenar) {
                IconButton(onClick = ordenar) {
                    Icon(
                        painterResource(id = R.drawable.baseline_swap_vert_24),
                        contentDescription = ""
                    )
                }
            }
        }
        Divider()
    }
    items(juegos) {
        ItemDeJuego(juegoClick, it)
    }

}

@Composable
private fun ItemDeJuego(
    juegoClick: (Int) -> Unit,
    it: Juego
) {
    Row(modifier = Modifier
        .clickable { juegoClick(it.identificador) }
        .size(Dp.Infinity, 140.dp)) {
        AsyncImage(model = it.miniatura, contentDescription = it.titulo)
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(
                text = it.titulo, style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )

            Text(
                text = it.descripcionCorta,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                maxLines = 2, overflow = TextOverflow.Ellipsis
            )
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = it.plataforma.uppercase(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = it.editor.uppercase(), style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Text(text = it.fechaDeLanzamiento)
        }
    }
}
