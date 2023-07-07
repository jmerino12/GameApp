package com.example.gameapp.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.example.dominio.juego.modelo.Juego
import com.example.gameapp.ui.compatido.CargandoContenido

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDeInicio(
    modifier: Modifier = Modifier,
    estadoDeUi: PantallaDeInicioEstadosDeUi
) {
    Scaffold { paddingValues ->
        ContenidoDePantallaDeInicio(
            modifier = modifier.padding(paddingValues),
            estadoDeUi.cargando,
            estadoDeUi.exito
        )
    }
}

@Composable
fun ContenidoDePantallaDeInicio(
    modifier: Modifier,
    cargando: Boolean,
    juegos: List<Juego>,
) {
    CargandoContenido(
        cargando = cargando,
        vacio = juegos.isEmpty() && !cargando,
        contenidoVacio = { Text(text = "No hay contenido") }) {
        LazyColumn {
            items(juegos) { juego ->
                Row {
                   AsyncImage(model = juego.miniatura, contentDescription = juego.titulo )
                    Column {
                        Text(text = juego.titulo)
                        Text(text = juego.descripcion)
                    }
                }
            }
        }
    }
}