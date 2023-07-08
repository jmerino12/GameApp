package com.example.infraestructura.compartido

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

class VerificadorDeInternet @Inject constructor(private val context: Context) {

    fun hayConexionInternet(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("/system/bin/ping -c 1 google.com")
            val exitCode = process.waitFor()
            exitCode == 0
        } catch (e: Exception) {
            false
        }
    }
}