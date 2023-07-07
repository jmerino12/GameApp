package com.example.gameapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gameapp.ui.pantallas.PantallaDeInicio
import com.example.gameapp.ui.pantallas.PantallaInicialViewModel


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
            PantallaDeInicio(
                modifier = modifier,
                estadoDeUi = estadoDeLaPantalla
            )
        }
    }
}
