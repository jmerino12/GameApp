package com.example.gameapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gameapp.ui.pantallas.detalle.PantallaDetalle
import com.example.gameapp.ui.pantallas.detalle.PantallaDetalleViewModel
import com.example.gameapp.ui.pantallas.inicio.PantallaDeInicio
import com.example.gameapp.ui.pantallas.inicio.PantallaInicialViewModel
import com.example.gameapp.ui.pantallas.inicio.TasksFilterType


@Composable
fun GameAppHostDeNavegacion(
    controladorDeNavegacion: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = controladorDeNavegacion,
        modifier = modifier,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            val viewModel = hiltViewModel<PantallaInicialViewModel>()
            val estadoDeLaPantalla by viewModel.estadoDeUi.collectAsStateWithLifecycle()
            val valorDelFiltro by viewModel.valorDelFiltro.collectAsStateWithLifecycle()
            PantallaDeInicio(
                modifier = modifier,
                estadoDeUi = estadoDeLaPantalla,
                juegoClick = {
                    controladorDeNavegacion.navegarAlDetalle(it)
                },
                filtroDeGenero = {
                    viewModel.seleccionarFiltro(TasksFilterType.JUEGOS_POR_GENERO, it)
                },
                filtroDeEditor = {
                    viewModel.seleccionarFiltro(
                        TasksFilterType.JUEGOS_POR_EDITOR,
                        it
                    )
                },
                resetearFiltro = {
                    viewModel.seleccionarFiltro(
                        TasksFilterType.TODOS_LOS_JUEGOS,
                        null
                    )
                },
                valorDelFiltro = valorDelFiltro,
                ordenar = { viewModel.ordenarPorFechaDeLanzamiento() }
            )
        }
        composable("detalle/?juego={juego}", arguments = listOf(
            navArgument("juego") { type = NavType.IntType }
        )) {
            val viewModel = hiltViewModel<PantallaDetalleViewModel>()
            val estadoDeLaPantalla by viewModel.estadoDeUi.collectAsStateWithLifecycle()
            PantallaDetalle(
                estadoDeUi = estadoDeLaPantalla,
                eliminarDeFavorita = { viewModel.eliminarDeFavorito() },
                peliculaFavorita = { viewModel.marcarComoFavorito() },
                mensajeDeError = estadoDeLaPantalla.mensajeDeError,
                error = estadoDeLaPantalla.error,
                mensajeMostrado = { viewModel.mensajeMostrado() }
            )
        }
    }
}

fun NavHostController.navegarAlDetalle(identificador: Int) =
    this.navigate("detalle/?juego=$identificador")
