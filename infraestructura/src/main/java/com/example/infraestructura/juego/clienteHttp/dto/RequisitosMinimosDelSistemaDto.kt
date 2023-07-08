package com.example.infraestructura.juego.clienteHttp.dto

import com.google.gson.annotations.SerializedName

data class RequisitosMinimosDelSistemaDto(
    @SerializedName("os") val sistemaOperativo: String,
    @SerializedName("processor") val procesador: String,
    @SerializedName("memory") val memoria: String,
    @SerializedName("graphics") val grafica: String,
    @SerializedName("storage") val almacenamiento: String,
)
