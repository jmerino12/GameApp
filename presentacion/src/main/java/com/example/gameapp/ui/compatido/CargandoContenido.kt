package com.example.gameapp.ui.compatido

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CargandoContenido(
    modifier: Modifier = Modifier,
    cargando: Boolean,
    vacio: Boolean,
    contenidoVacio: @Composable () -> Unit,
    contenido: @Composable () -> Unit

) {
    if (cargando) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else if (vacio) {
        contenidoVacio()
    } else {
        contenido()
    }
}