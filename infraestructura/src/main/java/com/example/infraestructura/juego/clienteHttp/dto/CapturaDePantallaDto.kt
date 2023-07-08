package com.example.infraestructura.juego.clienteHttp.dto

import com.google.gson.annotations.SerializedName

data class CapturaDePantallaDto(
    @SerializedName("id") val identificador: Int,
    @SerializedName("image") val imagen: String,
)