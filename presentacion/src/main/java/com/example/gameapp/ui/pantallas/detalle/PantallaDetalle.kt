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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dominio.juego.modelo.JuegoDetalle
import com.example.gameapp.R
import com.example.gameapp.ui.compatido.CargandoContenido
import com.example.gameapp.ui.compatido.ExpandableText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    modifier: Modifier = Modifier,
    estadoDeUi: PantallaDetalleEstadosDeUi,
    peliculaFavorita: () -> Unit,
    eliminarDeFavorita: () -> Unit,
    mensajeMostrado: () -> Unit,
    error: Boolean,
    mensajeDeError: Int?,
    estadoSnackBar: SnackbarHostState = SnackbarHostState(),

    ) {
    Scaffold(snackbarHost = { SnackbarHost(estadoSnackBar) }) { paddingValues ->
        mensajeDeError?.let { mensaje ->
            val snackbarText = stringResource(mensaje)
            LaunchedEffect(estadoSnackBar, mensaje, snackbarText) {
                estadoSnackBar.showSnackbar(snackbarText)
                mensajeMostrado()
            }
        }
        ContenidoDePantallaDetalle(
            modifier = modifier.padding(paddingValues),
            estadoDeUi.cargando,
            estadoDeUi.exito,
            peliculaFavorita,
            eliminarDeFavorita,
            error,
            mensajeDeError
        )
    }
}

@Composable
fun ContenidoDePantallaDetalle(
    modifier: Modifier,
    cargando: Boolean,
    juego: JuegoDetalle?,
    peliculaFavorita: () -> Unit,
    eliminarDeFavorita: () -> Unit,
    error: Boolean,
    mensajeDeError: Int?

) {
    CargandoContenido(
        cargando = cargando,
    ) {
        if (error && mensajeDeError != null) {
            Text(text = stringResource(id = mensajeDeError))
        } else if(juego != null){
            DetalleDelJuego(juego, peliculaFavorita, eliminarDeFavorita)
        }

    }
}

@Composable
private fun DetalleDelJuego(
    juego: JuegoDetalle?,
    peliculaFavorita: () -> Unit,
    eliminarDeFavorita: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            EncabezadoDeJuego(
                juego = juego,
                backButton = {},
                peliculaFavorita = peliculaFavorita,
                eliminarDeFavorita = eliminarDeFavorita
            )
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
                tituloDeLaSeccion = stringResource(R.string.requisitos_del_computador)
            ) {
                RequisitosDelSistema(juego = juego)
            }

            SeccionDelJuego(
                tituloDeLaSeccion = stringResource(R.string.capturas)
            ) {
                CarruselDeFotos(juego = juego)
            }
        }

    }
}

@Composable
private fun EncabezadoDeJuego(
    modifier: Modifier = Modifier,
    juego: JuegoDetalle?,
    backButton: () -> Unit,
    peliculaFavorita: () -> Unit,
    eliminarDeFavorita: () -> Unit
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
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = backButton) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = null
                )
            }

            IconButton(onClick = if (juego!!.favorito) eliminarDeFavorita else peliculaFavorita) {
                Icon(
                    imageVector = if (juego!!.favorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = Color.White,
                    contentDescription = null
                )
            }
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
        stringResource(R.string.sistema_operativo),
        juego?.requisitosMinimosDelSistema?.sistemaOperativo
    )
    RequisitoDelSistemItem(
        stringResource(R.string.procesador),
        juego?.requisitosMinimosDelSistema?.procesador
    )
    RequisitoDelSistemItem(
        stringResource(R.string.memoria),
        juego?.requisitosMinimosDelSistema?.memoria
    )

    RequisitoDelSistemItem(
        stringResource(R.string.espacio),
        juego?.requisitosMinimosDelSistema?.almacenamiento
    )

    RequisitoDelSistemItem(
        stringResource(R.string.grafica),
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