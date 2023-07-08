package com.example.gameapp.ui.pantallas.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dominio.juego.modelo.JuegoDetalle
import com.example.gameapp.ui.compatido.CargandoContenido
import com.example.gameapp.ui.compatido.ExpandableText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    modifier: Modifier = Modifier,
    estadoDeUi: PantallaDetalleEstadosDeUi,
) {
    Scaffold { paddingValues ->
        ContenidoDePantallaDetalle(
            modifier = modifier.padding(paddingValues),
            estadoDeUi.cargando,
            estadoDeUi.exito
        )
    }
}

@Composable
fun ContenidoDePantallaDetalle(
    modifier: Modifier,
    cargando: Boolean,
    juego: JuegoDetalle?,
) {
    CargandoContenido(
        cargando = cargando,
        vacio = juego == null && !cargando,
        contenidoVacio = { Text(text = "No hay contenido") }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                EncabezadoDeJuego(juego = juego, backButton = {})
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = juego?.titulo ?: "",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                ExpandableText(text = juego?.descripcion)
                val estilo = MaterialTheme.typography.labelSmall.toSpanStyle().copy(
                    background = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
                val urlDelJuego = buildAnnotatedString {
                    withStyle(estilo) {
                        append(juego?.urlJuego)
                    }
                }
                ClickableText(text = urlDelJuego, onClick = {})
                SeccionDelJuego(
                    tituloDeLaSeccion = "Requisitos del computador"
                ) {
                    RequisitosDelSistema(juego = juego)
                }

                SeccionDelJuego(
                    tituloDeLaSeccion = "Capturas"
                ) {
                    CarruselDeFotos(juego = juego)
                }
            }

        }
    }
}

@Composable
private fun EncabezadoDeJuego(
    modifier: Modifier = Modifier,
    juego: JuegoDetalle?,
    backButton: () -> Unit
) {

    Box(modifier = Modifier.wrapContentHeight(unbounded = true)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(juego?.miniatura)
                .crossfade(true)
                .build(),
            contentDescription = juego?.titulo,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(262.dp)

        )
        IconButton(onClick = backButton) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = Color.White,
                contentDescription = "Back"
            )
        }

    }


}

@Composable
private fun SeccionDelJuego(
    modifier: Modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
    tituloDeLaSeccion: String,
    contenido: @Composable () -> Unit,
) {
    Column(modifier) {
        Text(
            text = tituloDeLaSeccion.uppercase(),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onPrimary
            )
        )
        contenido()
    }

}

@Composable
private fun RequisitosDelSistema(
    modifier: Modifier = Modifier,
    juego: JuegoDetalle?,
) {

    RequisitoDelSistemItem(
        "Sistema Operativo: ",
        juego?.requisitosMinimosDelSistema?.sistemaOperativo
    )
    RequisitoDelSistemItem(
        "Procesador: ",
        juego?.requisitosMinimosDelSistema?.procesador
    )
    RequisitoDelSistemItem(
        "Memoria: ",
        juego?.requisitosMinimosDelSistema?.memoria
    )

    RequisitoDelSistemItem(
        "Espacio: ",
        juego?.requisitosMinimosDelSistema?.almacenamiento
    )

    RequisitoDelSistemItem(
        "Grafica: ",
        juego?.requisitosMinimosDelSistema?.grafica
    )


}

@Composable
private fun RequisitoDelSistemItem(valor: String, contenido: String?) {
    Row {
        Text(text = valor)
        Text(text = contenido!!)
    }
}

@Composable
private fun CarruselDeFotos(
    modifier: Modifier = Modifier,
    juego: JuegoDetalle?,
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(juego!!.capturaDePantalla) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(it.imagen)
                    .crossfade(true)
                    .build(),
                contentDescription = it.identificador.toString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp, 90.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }
}